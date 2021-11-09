import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseConnection {
    private static final String DATA_BASE_URL = "jdbc:mysql://localhost/";
    private static final String USER = "admin";
    private static final String PASS_WORD = "admin123!";
    private static Connection conn;

    public static void connectAndSetupDataBase() {
        try {
            conn = DriverManager.getConnection(DATA_BASE_URL, USER, PASS_WORD);
            System.out.println("Database connected successfully...");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createParrotDataBase() {

    }


}
