package br.com.file.analytic;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.support.TestPropertySourceUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SmokeTestContextInitializer implements
        ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        try {
            Path inputDir = Files.createTempDirectory("input");
            Path outputDir = Files.createTempDirectory("output");

            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                    configurableApplicationContext, "inputDir=" + inputDir.toString());
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                    configurableApplicationContext, "outputDir=" + outputDir.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
