package dao;

import util.HolidayDBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HolidayDao implements IHolidayDao{
    private static final HolidayDBConnection DB_CONNECTION = new HolidayDBConnection();

    public HolidayDao() {
    }

    @Override
    public ResultSet findAll() {
        String QUERY = "SELECT * FROM holidays";
        try (Connection connection = DB_CONNECTION.getConnection()) {
            Statement stmt = connection.createStatement();
            return stmt.executeQuery(QUERY);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResultSet findAllByMonths() {
        String QUERY = "SELECT id, country, name, EXTRACT(MONTH FROM date) AS date FROM holidays";
        try (Connection connection = DB_CONNECTION.getConnection()) {
            Statement stmt = connection.createStatement();
            return stmt.executeQuery(QUERY);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResultSet countAllHolidaysPerMonth() {
        String QUERY = "SELECT country, EXTRACT(MONTH FROM date) AS direction , TO_CHAR(date, 'Mon') AS month, COUNT(name) AS count " +
                        "FROM holidays " +
                        "GROUP BY direction, month, country " +
                        "ORDER BY direction";
//        String QUERY = "SELECT country, EXTRACT(MONTH FROM date) AS month, COUNT(name) AS count FROM holidays GROUP BY month, country ORDER BY month";
        try (Connection connection = DB_CONNECTION.getConnection()) {
            Statement stmt = connection.createStatement();
            return stmt.executeQuery(QUERY);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
