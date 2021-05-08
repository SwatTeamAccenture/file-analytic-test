package br.com.file.analytic.report;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class RegexReportEntryParse implements ReportEntryParser {

    protected abstract Pattern getPattern();
    protected abstract Report updateReport(Report report, Matcher matcher) throws Exception;

    @Override
    public boolean canParse(String entry) {
        return getPattern().matcher(entry).matches();
    }

    @Override
    public Report parse(Report report, String entry) throws Exception {
        Matcher matcher = getPattern().matcher(entry);

        if(!matcher.matches()) {
            throw new IllegalArgumentException("Entry must match expression");
        }

        return updateReport(report, matcher);
    }
}
