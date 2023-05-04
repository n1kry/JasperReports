package reports;

import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.constant.Calculation;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import services.HolidayService;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import static util.ReportStyleConsts.*;

public class CrosstabReport implements Reportable {

    private final HolidayService service = new HolidayService();

    @Override
    public void start() {
        JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(service.findAllByMonths());
        JRResultSetDataSource resultSetDataSourceChart = new JRResultSetDataSource(service.countAllHolidaysPerMonth());

        TextColumnBuilder<Integer> monthColumn = col.column("month", "date",type.integerType());

        try {
            report()
                    .title(cmp.horizontalList(cmp.text("Holiday").setStyle(TITLE_STYLE), cmp.image("src/main/resources/img/logo.png")))
                    .setTitleStyle(stl.style().setLeftPadding(20).setRightPadding(20).setTopPadding(20))
                    .summary(
                             ctab.crosstab()
                                    .headerCell(cmp.text("State/Messe").setStyle(BOLD_HEADER_CELL_BORDERED))
                                    .addRowGroup(ctab.rowGroup("country", String.class).setTotalHeaderStyle(TOTAL_BORDERED.setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT)))
                                    .addColumnGroup(ctab.columnGroup(monthColumn).setTotalHeaderStyle(TOTAL_BORDERED))
                                    .addMeasure(ctab.measure("id", Integer.class, Calculation.COUNT))
                                    .setCellWidth(30)
                                    .setStyle(stl.style().setBottomPadding(40))
                                    .setCellStyle(CELLS_BORDERED)
                                    .setGrandTotalStyle(GRAND_TOTAL_BORDERED)
                                    .setGroupStyle(HEADER_BORDERED),

                            cht.barChart()
                                    .setTitle("Holidays per month:")
                                    .setCategory(col.column("month", String.class))
                                    .series(
                                            cht.serie(col.column("Holidays count", "count", Integer.class))
                                                    .setSeries(col.column("Countries", "country", String.class))
                                    )
                                    .setDataSource(resultSetDataSourceChart)
                    )
                    .setSummaryStyle(stl.style().setPadding(20))
                    .setDataSource(resultSetDataSource)
                    .show();
        } catch (DRException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        CrosstabReport report = new CrosstabReport();
        report.start();
    }
}
