package reports;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import services.HolidayService;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.*;

public class JRMapExample implements Reportable{
    private final HolidayService service = new HolidayService();
    @Override
    public void start() {
        try {
            // fill report
            List<Map<String,?>> maps = new ArrayList<>(service.findAllToListMaps());
            System.out.println(maps);
            JRMapCollectionDataSource dataSource = new JRMapCollectionDataSource(maps);

            // compile report
            Reportable.showReport(dataSource);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JRMapExample main = new JRMapExample();
        main.start();
    }
}
