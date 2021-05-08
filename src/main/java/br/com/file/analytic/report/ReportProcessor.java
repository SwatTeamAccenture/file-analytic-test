package br.com.file.analytic.report;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

import java.io.File;

public class ReportProcessor implements Processor {
    private Report report;

    public ReportProcessor() {
        this.report = new Report();
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        Message message = exchange.getMessage();
        File inputFile = message.getBody(File.class);


        message.setBody(report);
    }

}
