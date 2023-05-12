package services;

import dao.HolidayDao;
import dao.IHolidayDao;
import model.Holiday;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static constants.JasperConsts.*;

public class HolidayService {
    private final IHolidayDao dao = new HolidayDao();

    public List<Map<String,?>> findAllToListMaps() throws SQLException {
        ResultSet resultSet = dao.findAll();
        List<Map<String,?>> maps = new ArrayList<>();
        while(resultSet.next()) {
                Map<String,Object> map = new HashMap<>();
                map.put(COUNTRY_FIELD, resultSet.getString(COUNTRY_FIELD));
                map.put(DATE_FIELD, resultSet.getDate(DATE_FIELD));
                map.put(NAME_FIELD, resultSet.getString(NAME_FIELD));
                maps.add(map);
        }
        return maps;
    }

    public List<Holiday> findAllToListBeans() throws SQLException {
        ResultSet resultSet = dao.findAll();
        List<Holiday> holidays = new ArrayList<>();
        while (resultSet.next()) {
            Holiday holiday = new Holiday();
            holiday.setCountry(resultSet.getString(COUNTRY_FIELD));
            holiday.setDate(resultSet.getDate(DATE_FIELD));
            holiday.setName(resultSet.getString(NAME_FIELD));
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
