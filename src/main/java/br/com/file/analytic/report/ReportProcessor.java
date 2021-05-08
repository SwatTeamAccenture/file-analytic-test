package br.com.file.analytic.report;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ReportProcessor implements Processor {
    private ReportService reportService;
    private Report report;

    public ReportProcessor(ReportService reportService) {
        this.reportService = reportService;
        this.report = new Report();
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        Message message = exchange.getMessage();
        File inputFile = message.getBody(File.class);

        BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            reportService.parse(report, line);
        }

        report.setWorstSalesmanProperties();

        message.setBody(report);
    }

}
