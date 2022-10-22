package main;

import java.sql.Connection;
import  java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import main.LOBInterpreter;

import static main.LOBInterpreter.getcolumns;
import static main.LOBInterpreter.readLOB;

public class PostGresDatabase {
    private static Map<String , String> columns = new HashMap<>();

    public static Connection connection(){
        Connection c = null;
        try{
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/serialized-lob",
                            "postgres", "123");
        }catch (Exception e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        return c;
    }

    public static  void toSerializedLOB(Connection c, String tableName, String newTableName){
        connection();
        Statement create = null;
        Statement insert = null;
        StringBuilder sql = new StringBuilder();
        try{
            String lob = PostGresSerializer.graphToLOB(c,tableName);
            columns = getcolumns(readLOB(lob));
            sql.append("Create TABLE ").append(newTableName).append(" ");
            create = c.createStatement();

            sql.append("(").append(newTableName).append(")");

            create.executeUpdate(sql.toString());
            create.close();

            insert = c.createStatement();
            sql = new StringBuilder();
            sql.append("INSERT INTO ").append(newTableName).append(" (").
                    append(newTableName).append(") ").
                    append("VALUES (").append(lob).append(")");
            insert.executeUpdate(sql.toString());
            c.close();

        } catch (Exception e){
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }
}
