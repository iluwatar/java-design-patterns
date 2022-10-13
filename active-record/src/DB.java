import java.sql.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
//import com.mysql.jdbc.Connection;
//import com.mysql.jdbc.PreparedStatement;
//import com.mysql.jdbc.Statement;
import java.sql.*;

public class DB {
    final String dbDomain;
    final String dbName;
    final String username;
    final String password;
    final Connection connection;

    public DB(String dbName, String username, String password, String dbDomain) throws SQLException {
        this.dbDomain = dbDomain;
        this.dbName = dbName;
        this.username = username;
        this.password = password;
        this.connection = DriverManager.getConnection(
                "jdbc:mysql://" + this.dbDomain + "/" +this.dbName,
                this.username,
                this.password);
    }


    public static void Write() throws SQLException {

    }
    public void Read() throws SQLException {
        Statement statement = this.connection.createStatement();

        ResultSet resultSet = statement.executeQuery("select * from people");

        while (resultSet.next()){
            System.out.println(resultSet.getString("firstname"));
        }

    }
    public static void Update() throws SQLException {

    }
}
