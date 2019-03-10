package bookstore.repository.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class MySqlDatabaseConnector {
    private final static String CONNECTION_STRING = "jdbc:mysql://127.0.0.1:3306/book_store";
    private final static String USER = "root";
    private final static String PASSWORD = "";
    private static Connection connection;

    private MySqlDatabaseConnector() {
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
        }
        return connection;
    }
}