package br.com.file.analytic.report;

public interface ReportEntryParser {

    boolean canParse(String entry);

    Report parse(Report report, String entry) throws Exception;

}
