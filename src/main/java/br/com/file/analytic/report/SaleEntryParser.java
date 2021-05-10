package br.com.file.analytic.report;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An {@link ReportEntryParser} for sale entries
 */
@Component
public class SaleEntryParser extends RegexReportEntryParse {
    private final Pattern pattern = Pattern.compile("^003ç(\\d+)ç\\[(.+)\\]ç([ \\w]+)$");
    private final Pattern salePattern = Pattern.compile("(\\d+)-(\\d+)-([\\.\\d]+)");

    @Override
    protected Pattern getPattern() {
        return pattern;
    }

    @Override
    protected Report updateReport(Report report, Matcher matcher) throws Exception {
        int id = Integer.parseInt(matcher.group(1));
        String salesStr = matcher.group(2);
        String salesman = matcher.group(3);

        Matcher salesMatcher = salePattern.matcher(salesStr);

        BigDecimal totalvalue = BigDecimal.ZERO;
        while (salesMatcher.find()) {
            totalvalue = totalvalue.add(new BigDecimal(salesMatcher.group(2))
                    .multiply(new BigDecimal(salesMatcher.group(3))));
        }

        report.addSale(id, totalvalue, salesman);

        return report;
    }
}
