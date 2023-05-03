package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class HolidayDBConnection {
    private final String url = "jdbc:postgresql://localhost:5433/holiday";
    private final String user = "postgres";
    private final String password = "1212";

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
