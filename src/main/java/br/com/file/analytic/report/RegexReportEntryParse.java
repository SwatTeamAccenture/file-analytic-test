package br.com.file.analytic.report;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A {@link ReportEntryParser} that uses a regular expression to check and extract information
 * from an entry
 */
public abstract class RegexReportEntryParse implements ReportEntryParser {

    /**
     * Implementations of this method must return a {@link Pattern} to be used to be used to check and
     * extract information from an entry
     *
     * @return
     */
    protected abstract Pattern getPattern();

    /**
     * updates the report with the information extracted from the entry
     *
     * @param report   the report to be updated
     * @param matcher  the initialized matcher returned by applying the {@link Pattern} to the entry
     * @return  the same report reference
     * @throws Exception
     */
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
