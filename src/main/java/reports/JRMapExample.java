package reports;

import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import services.HolidayService;

import java.util.*;

public class JRMapExample implements Reportable{
    private final HolidayService service = new HolidayService();
    @Override
    public void start() {
        try {
            List<Map<String,?>> maps = new ArrayList<>(service.findAllToListMaps());

            JRMapCollectionDataSource dataSource = new JRMapCollectionDataSource(maps);

            Reportable.showReportFromDataSource(dataSource);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JRMapExample main = new JRMapExample();
        main.start();
    }
}
