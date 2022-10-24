import java.sql.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
//import com.mysql.jdbc.Connection;
//import com.mysql.jdbc.PreparedStatement;
//import com.mysql.jdbc.Statement;
import java.sql.*;
import java.util.ArrayList;

public class DB {
    final String dbDomain;
    final String dbName;
    final String username;
    final String password;
    final String tableName;
    final String ID;
    final Connection connection;

    public DB(String dbName, String username, String password, String dbDomain, String tableName, String ID) throws SQLException {
        this.dbDomain = dbDomain;
        this.dbName = dbName;
        this.username = username;
        this.password = password;
        this.tableName = tableName;
        this.ID = ID;
        this.connection = DriverManager.getConnection(
                "jdbc:mysql://" + this.dbDomain + "/" +this.dbName,
                this.username,
                this.password);


    }


    public static void Write() throws SQLException {

    }

    public String Read() throws SQLException {
        Statement statement = this.connection.createStatement();

        ResultSet resultSet = statement.executeQuery("select * from `world`.`city`");
        ResultSet columnSet = statement.executeQuery("SELECT * FROM `"+this.dbName+"`.`"+this.tableName+"`");
        ArrayList<String> columnNames = new ArrayList<String>();
        ResultSetMetaData rsmd = columnSet.getMetaData();
        int columnCount = rsmd.getColumnCount();
        for (int i = 1; i < columnCount+1; i++) {
            String name = rsmd.getColumnName(i);
            columnNames.add(name);
        }


        return columnNames.get(0);
    }
    public static void Update() throws SQLException {

    }

    public static void main(String[] args) throws SQLException {
        DB db = new DB("world","root","apple-trunks","localhost:3306", "city", "1");
        System.out.println(db.Read());
    }
}
