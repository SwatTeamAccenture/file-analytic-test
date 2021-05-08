package br.com.file.analytic.report;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ReportRouteBuilder extends RouteBuilder {
    private static final String REPORT_FILE_ENDPOINT = "seda:reportFile";
    private static final String DELETE_REPORT_ENDPOINT = "seda:deleteReport";

    private Path inputPath;
    private Path outputPath;

    public ReportRouteBuilder(Path inputPath, Path outputPath) throws IOException, IllegalArgumentException {
        this.inputPath = inputPath;
        this.outputPath = outputPath;
        initDirs();
    }

    public ReportRouteBuilder(CamelContext context, Path inputPath, Path outputPath)
            throws IOException, IllegalArgumentException {
        super(context);
        this.inputPath = inputPath;
        this.outputPath = outputPath;
        initDirs();
    }

    @Override
    public void configure() throws Exception {
        from("file-watch://" + inputPath.toString())
                .choice()
                .when(header("CamelFileEventType").isEqualTo("CREATE"))
                .to(REPORT_FILE_ENDPOINT)
                .when(header("CamelFileEventType").isEqualTo("UPDATE"))
                .to(REPORT_FILE_ENDPOINT)
                .when(header("CamelFileEventType").isEqualTo("DELETE"))
                .to(DELETE_REPORT_ENDPOINT)
                .otherwise()
                .to("log:invalidEventError");

        from(REPORT_FILE_ENDPOINT)
                .convertBodyTo(String.class)
                .setHeader("report", Report::new)
                .split().tokenize("\n",1)
                .to("stream:out");
        from(DELETE_REPORT_ENDPOINT).to("stream:out");
    }

    private void initDirs() throws IOException, IllegalArgumentException {
        initDir(inputPath);
        initDir(outputPath);
    }

    private void initDir(Path dir) throws IOException, IllegalArgumentException {
        if (Files.exists(dir) && !Files.isDirectory(dir)) {
            throw new IllegalArgumentException("The input and output paths must not be of a file.");
        }

        if (!Files.exists(dir)) {
            Files.createDirectories(dir);
        }
    }

}
