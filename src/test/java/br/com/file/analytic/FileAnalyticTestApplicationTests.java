package br.com.file.analytic;

import br.com.file.analytic.report.Report;
import br.com.file.analytic.report.ReportRouteBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RunWith(SpringRunner.class)
@SpringBootTest()
@ContextConfiguration(
        initializers = SmokeTestContextInitializer.class,
        classes = Application.class)
public class FileAnalyticTestApplicationTests extends TestCase {

    @Autowired
    private ApplicationContext applicationContext;

    private Path inputDir;
    private Path outputDir;

    @Before
    public void startApplication() throws Exception {
        ReportRouteBuilder reportRouteBuilder = applicationContext.getBean(ReportRouteBuilder.class);
        inputDir = reportRouteBuilder.getInputPath();
        outputDir = reportRouteBuilder.getOutputPath();
    }

    @After
    public void stopApplication() throws IOException {
        FileUtils.deleteDirectory(inputDir.toFile());
        FileUtils.deleteDirectory(outputDir.toFile());
    }

    @Test
    public void reportingSmokeTest() throws Exception {
        String filename = "exampleValidInput";
        Path inputFile = getInputFile(filename);
        Path outputFile = outputDir.resolve(filename);

        Files.copy(inputFile, inputDir.resolve(filename));
        // Aguarda o arquivo de input ser processado
        Thread.sleep(10000);

        assertTrue(Files.exists(outputFile));
        assertTrue(Files.isRegularFile(outputFile));

        Report report = new ObjectMapper()
                .readerFor(Report.class)
                .readValue(IOUtils.toString(Files.newBufferedReader(outputFile)));

        assertEquals(2, report.getSalesmanCount());
        assertEquals(2, report.getClientCount());
        assertEquals(10, report.getMostExpensiveSaleId());
        assertEquals("3245678865434", report.getWorstSalesmanCPF());
        assertEquals("Paulo", report.getWorstSalesmanName());
    }

    private Path getInputFile(String filename) {
        ClassLoader classLoader = getClass().getClassLoader();
        return Paths.get(classLoader.getResource(filename).getPath());
    }

}
