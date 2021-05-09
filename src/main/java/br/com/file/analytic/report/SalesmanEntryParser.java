package br.com.file.analytic.report;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An {@link ReportEntryParser} for salesman entries
 */
@Component
public class SalesmanEntryParser extends RegexReportEntryParse {
    private final Pattern pattern = Pattern.compile("^001ç(\\d{13})ç([ \\w]+)ç([0-9\\.]+)$");

    @Override
    protected Pattern getPattern() {
        return pattern;
    }

    @Override
    protected Report updateReport(Report report, Matcher matcher) throws Exception {
        report.addSalesman(matcher.group(1), matcher.group(2));
        return report;
    }
}
