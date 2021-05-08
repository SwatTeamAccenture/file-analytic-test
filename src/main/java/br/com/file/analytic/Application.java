package br.com.file.analytic;

import br.com.file.analytic.report.ReportRouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
@ComponentScan("br.com.file.analytic.report")
public class Application {
    private static ApplicationContext springContext;

    public static void main(String[] args) {
        springContext = new SpringApplicationBuilder(Application.class).run(args);
    }

}
