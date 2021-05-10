package br.com.file.analytic.report;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An {@link ReportEntryParser} for client entries
 */
@Component
public class ClientEntryParser extends RegexReportEntryParse {
    private final Pattern pattern = Pattern.compile("^002ç(\\d{16})ç([ \\w]+)ç([ \\w]+)$");

    @Override
    protected Pattern getPattern() {
        return pattern;
    }

    @Override
    protected Report updateReport(Report report, Matcher matcher) {
        report.addClient();
        return report;
    }
}
