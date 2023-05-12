package reports;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.*;
import java.util.HashMap;

import static constants.JasperConsts.REPORT_TEMPLATE_PATH;
import static constants.JasperConsts.OUTPUT_FILE_PATH;

public interface Reportable {
    void start() throws FileNotFoundException, JRException;
    static void showReportFromDataSource(JRDataSource dataSource) {
        try {
            FileInputStream fis = new FileInputStream(REPORT_TEMPLATE_PATH);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fis);

            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(bufferedInputStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), dataSource);
            OutputStream outputStream = new FileOutputStream(OUTPUT_FILE_PATH + dataSource.getClass().getSimpleName()+ ".pdf");

            // Write content to PDF file
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

            // view report to UI
            JasperViewer.viewReport(jasperPrint, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
