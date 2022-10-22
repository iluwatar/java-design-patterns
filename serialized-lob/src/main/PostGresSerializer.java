package main;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* From an undirected jgrapht graph, create a single large object.
For this implementation it will  be a single String(CLOB).
 */
public class PostGresSerializer {
    public static String graphToLOB(Connection c, String tableName){
        StringBuilder lob = new StringBuilder();
        Statement stmt = null;
        String sql = "\\d+ " + tableName;
        Map<String,String> columns = new HashMap<>();
        try{
            ResultSet columnSets = stmt.executeQuery(sql);
            String col = "";
            String type = "";

            while(columnSets.next()){
                col = columnSets.getString("columns");
                type = columnSets.getString("Type");
                columns.put(col,type);
            }
            columnSets.close();

            int size = columns.size();
            lob.append(String.valueOf(size));
            for(String s:columns.keySet()){
                lob.append(" ").append(s);
                lob.append(" ").append(columns.get(col));
            }

        } catch (Exception e){
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }

        sql = "SELECT * FROM " + tableName;
        try{
            ResultSet graph = stmt.executeQuery(sql);

            while(graph.next()){
                for (String s : columns.keySet()){
                     lob.append(" ").append(graph.getString(s));
                }
            }

            graph.close();
        }catch (Exception e){
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }

        return lob.toString();
    }
}
