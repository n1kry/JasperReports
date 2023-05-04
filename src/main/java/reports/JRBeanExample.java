package reports;

import model.Holiday;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import services.HolidayService;

import java.util.List;

public class JRBeanExample implements Reportable{
    private final HolidayService service = new HolidayService();
    @Override
    public void start() {
        try {
            List<Holiday> holidays = service.findAllToListBeans();

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(holidays);

            Reportable.showReportFromDataSource(dataSource);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        JRBeanExample main = new JRBeanExample();
        main.start();
    }
}
