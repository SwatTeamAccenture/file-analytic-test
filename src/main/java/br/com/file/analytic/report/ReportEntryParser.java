package br.com.file.analytic.report;

/**
 * Represents a possible entry parser for report inputs
 */
public interface ReportEntryParser {

    /**
     * Checks if this instance is able to parse a given entry
     *
     * @param entry  the entry to be parsed
     * @return  whether or not this instance can parse the entry
     */
    boolean canParse(String entry);

    /**
     * Parse an entry and update it's report
     *
     * @param report  the report to be updated
     * @param entry   the entry to be parsed
     * @return  the same report reference
     * @throws Exception
     */
    Report parse(Report report, String entry) throws Exception;

}
