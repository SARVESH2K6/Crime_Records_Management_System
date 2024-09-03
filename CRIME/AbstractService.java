package CRIME;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class AbstractService {
    protected Connection conn;

    public AbstractService() throws SQLException {
        this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/crime_records", "root", "");
    }

    public void closeConnection() throws SQLException {
        if (this.conn != null && !this.conn.isClosed()) {
            this.conn.close();
        }
    }
}
