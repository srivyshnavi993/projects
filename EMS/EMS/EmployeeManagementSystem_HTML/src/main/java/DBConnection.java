import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
   private static final String URL =
    "jdbc:mysql://localhost:3306/employee_db?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "vyshnavi";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
