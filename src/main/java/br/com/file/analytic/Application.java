package br.com.file.analytic;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * Spring Boot Application starter
 */
@SpringBootApplication
@ComponentScan("br.com.file.analytic.report")
public class Application {
    private static ApplicationContext springContext;

    public static void main(String[] args) {
        springContext = new SpringApplicationBuilder(Application.class).run(args);
    }

}
