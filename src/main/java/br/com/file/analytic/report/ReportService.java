package br.com.file.analytic.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {
    @Autowired
    private List<ReportEntryParser> reportEntryParsers;

    public Report parse(Report report, String entry) throws Exception {
        ReportEntryParser parser = reportEntryParsers.stream()
                .filter(reportEntryParser -> reportEntryParser.canParse(entry))
                .findFirst().orElse(null);

        return parser != null ? parser.parse(report, entry) : report;
    }

}
