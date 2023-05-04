package services;

import dao.HolidayDao;
import dao.IHolidayDao;
import model.Holiday;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.*;

public class HolidayService {
    private final IHolidayDao dao = new HolidayDao();

    public List<Map<String,?>> findAllToListMaps() throws SQLException {
        ResultSet resultSet = dao.findAll();
        List<Map<String,?>> maps = new ArrayList<>();
        while(resultSet.next()) {
                Map<String,Object> map = new HashMap<>();
                map.put("country", resultSet.getString("country"));
                map.put("date", resultSet.getDate("date"));
                map.put("name", resultSet.getString("name"));
                maps.add(map);
        }
        return maps;
    }

    public List<Holiday> findAllToListBeans() throws SQLException {
        ResultSet resultSet = dao.findAll();
        List<Holiday> holidays = new ArrayList<>();
        while (resultSet.next()) {
            Holiday holiday = new Holiday();
            holiday.setCountry(resultSet.getString("country"));
            holiday.setDate(resultSet.getDate("date"));
            holiday.setName(resultSet.getString("name"));
            holidays.add(holiday);
        }
        return holidays;
    }

    public ResultSet findAll() {
        return dao.findAll();
    }

    public ResultSet findAllByMonths() {
        return dao.findAllByMonths();
    }

    public ResultSet countAllHolidaysPerMonth() {
        return dao.countAllHolidaysPerMonth();
    }
}
