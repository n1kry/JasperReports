package reports;
import constants.JasperConsts;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import services.HolidayService;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import static constants.ReportStyleConsts.*;
import static constants.JasperConsts.*;

public class DynamicReport implements Reportable{
    private final HolidayService service = new HolidayService();

    @Override
    public void start() {
        JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(service.findAll());
        try {
            report()
                    .setDataSource(resultSetDataSource)
//                    .columns(
//                            col.column("Country", "country", type.stringType()).setTitleStyle(BOLD_BORDERED).setTitleHeight(30),
//                            col.column("Name", "name", type.stringType()).setTitleStyle(BOLD_BORDERED).setHeight(30),
//                            col.column("Date", "date", type.dateType()).setTitleStyle(BOLD_BORDERED)
//                    ).setColumnHeaderStyle(HEADER_STYLE).setColumnStyle(DETAIL_BORDERED)
                    .pageHeader(
                            cmp.horizontalList(
                                    cmp.text("Country").setStyle(BOLD_BORDERED),
                                    cmp.text("Name").setStyle(BOLD_BORDERED),
                                    cmp.text("Date").setStyle(BOLD_BORDERED)
                                    ).setHeight(30).setStyle(HEADER_STYLE)
                    )
                    .fields(field(COUNTRY_FIELD, type.stringType()),field(DATE_FIELD, type.dateType()), field(NAME_FIELD, type.stringType()))
                    .detail(
                            cmp.horizontalList(
                                    cmp.text(exp.jasperSyntax("$F{"+COUNTRY_FIELD+"}")).setStyle(DETAIL_BORDERED),
                                    cmp.text(exp.jasperSyntax("$F{"+NAME_FIELD+"}")).setMinHeight(30).setStyle(DETAIL_BORDERED),
                                    cmp.text(exp.jasperSyntax("$F{"+DATE_FIELD+"}")).setStyle(DETAIL_BORDERED)
                                    )
                    )
                    .title(cmp.horizontalList(cmp.text("Holiday").setStyle(TITLE_STYLE), cmp.image(JasperConsts.IMAGE_PATH)))
                    .pageFooter(cmp.pageXofY())
                    .show();
        } catch (DRException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        DynamicReport dynamicReport = new DynamicReport();
        dynamicReport.start();
    }
}
