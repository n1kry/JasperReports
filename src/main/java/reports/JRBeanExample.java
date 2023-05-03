package reports;

import model.Holiday;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import services.HolidayService;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class JRBeanCollectionDataSourceExample implements Reportable{
    private final HolidayService service = new HolidayService();
    @Override
    public void start() {
        try {
            FileInputStream fis = new FileInputStream("src/main/resources/reports/holydays.jasper");
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fis);

            List<Holiday> holidays = service.findAllToListBeans();

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(holidays);

            // compile report
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(bufferedInputStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), dataSource);

            // view report to UI
            JasperViewer.viewReport(jasperPrint, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        JRBeanCollectionDataSourceExample main = new JRBeanCollectionDataSourceExample();
        main.start();
    }
}
