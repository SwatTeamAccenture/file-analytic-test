package br.com.file.analytic;

import br.com.file.analytic.report.ReportRouteBuilder;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Application {
    public static final String DEFAULT_INPUT_DIR = "data/in";
    public static final String DEFAULT_OUTPUT_DIR = "data/out";

    private CamelContext camelContext;

    public static void main(String[] args) {
        Application application = null;
        try {
            application = new Application();
            application.start();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(application != null) {
                application.stop();
            }
        }

    }

    public Application() throws Exception {
        Path userPath = Paths.get(System.getProperty("user.home"));
        init(userPath.resolve(DEFAULT_INPUT_DIR), userPath.resolve(DEFAULT_INPUT_DIR));
    }

    public Application(Path inputDir, Path outputDir) throws Exception {
        init(inputDir, outputDir);
    }

    private void init(Path inputDir, Path outputDir) throws Exception {
        camelContext = new DefaultCamelContext();
        ReportRouteBuilder reportRouteBuilder = new ReportRouteBuilder(inputDir, outputDir);
        camelContext.addRoutes(reportRouteBuilder);
    }

    public void start() {
        camelContext.start();
    }

    public void stop() {
        camelContext.stop();
    }

}
