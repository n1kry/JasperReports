package reports;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.*;
import java.util.HashMap;

public interface Reportable {
    void start() throws FileNotFoundException, JRException;
    static void showReportFromDataSource(JRDataSource dataSource) {
        try {
            FileInputStream fis = new FileInputStream("src/main/resources/reports/holydays.jasper");
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fis);

            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(bufferedInputStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), dataSource);
            OutputStream outputStream = new FileOutputStream("src/main/resources/reports/holydays" + dataSource.getClass().getSimpleName()+ ".pdf");

            // Write content to PDF file
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

            // view report to UI
            JasperViewer.viewReport(jasperPrint, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
