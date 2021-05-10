package br.com.file.analytic.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {
    @Autowired
    private List<ReportEntryParser> reportEntryParsers;

    /**
     * Finds the appropriate {@link ReportEntryParser} bean to parse an entry and update the report
     * with it.
     *
     * @param report  the report to be updated
     * @param entry   the entry to be parsed
     * @return  the same report reference
     * @throws Exception
     */
    public Report parse(Report report, String entry) throws Exception {
        ReportEntryParser parser = reportEntryParsers.stream()
                .filter(reportEntryParser -> reportEntryParser.canParse(entry))
                .findFirst().orElse(null);

        return parser != null ? parser.parse(report, entry) : report;
    }

}
