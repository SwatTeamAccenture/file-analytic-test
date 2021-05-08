package br.com.file.analytic;

import br.com.file.analytic.report.Report;
import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.TestCase;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RunWith(JUnit4.class)
public class FileAnalyticTestApplicationTests extends TestCase {

    private Path inputDir;
    private Path outputDir;

    @Before
    public void startApplication() throws Exception {
        inputDir = Files.createTempDirectory("input");
        outputDir = Files.createTempDirectory("output");
    }

    @After
    public void stopApplication() throws IOException {
        Files.delete(inputDir);
        Files.delete(outputDir);
    }

    @Test
    public void reportingSmokeTest() throws Exception {
        Application application = new Application(inputDir, outputDir);
        application.start();

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
        assertEquals("Paulo", report.getWorstSalesmanCPF());
    }

    private Path getInputFile(String filename) {
        ClassLoader classLoader = getClass().getClassLoader();
        return Paths.get(classLoader.getResource(filename).getPath());
    }

}
