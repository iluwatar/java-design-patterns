package com.iluwatar.activeobject;
import java.sql.*;
import java.util.ArrayList;

/**
 * ActiveRow attempts to implement the fundamental ruby on rails 'Active Record' design pattern in Java.
 */
public class ActiveRow {
    String ID;
    String IDName;
    DB dataBase;
    ArrayList<String> columns;
    ArrayList<String> contents = new ArrayList<String>();

    /**
     * @param dataBase A Database object which handles opening the connection.
     * @param ID The unique identifier of a row.
     * @throws SQLException
     */
    public ActiveRow(DB dataBase, String ID) throws SQLException {
        this.ID = ID;
        Statement statement = dataBase.connection.createStatement();
        ResultSet columnSet = statement.executeQuery("SELECT * FROM `"+dataBase.dbName+"`.`"+dataBase.tableName+"`");
        ArrayList<String> columnNames = new ArrayList<String>();
        ResultSetMetaData rsmd = columnSet.getMetaData();
        int columnCount = rsmd.getColumnCount();
        for (int i = 1; i < columnCount+1; i++) {
            String name = rsmd.getColumnName(i);
            columnNames.add(name);
        }
        this.columns = columnNames;
        ResultSet rowResult = statement.executeQuery("SELECT * FROM `"+dataBase.dbName+"`.`"+dataBase.tableName+"` WHERE ID="+this.ID);
        while (rowResult.next())
        {
            for (int col=1; col <= columnCount; col++)
            {
                Object value = rowResult.getObject(col);
                if (value != null) {
                    contents.add(value.toString());
                }
            }
        }
        rowResult.close();
        statement.close();
        columnSet.close();
        dataBase.connection.close();
    }


    /**
     *
     * @throws SQLException
     */
    @Rowverride
    public void Write() throws SQLException {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO `").append(dataBase.dbName).append("`.`").append(dataBase.tableName).append("`(");
        for (String col: this.columns){
            if(columns.indexOf(col) != col.length()){
                query.append(col);
                query.append(",");
            } else{
                query.append(col);
                query.append(") VALUES (");
            }
        }
        for (String con: this.contents){
            if(columns.indexOf(con) != con.length()){
                query.append(con);
                query.append(",");
            } else{
                query.append(con);
                query.append(")");
            }
        }
        PreparedStatement stmt = dataBase.connection.prepareStatement(query.toString());
        stmt.executeUpdate();
    }

    @Rowverride
    public void Delete() throws SQLException {
        PreparedStatement st = dataBase.connection.prepareStatement("DELETE FROM `"+dataBase.dbName+"`.`"+dataBase.tableName +"`" + " WHERE ID = '" + this.ID + "';");
    }

    @Rowverride
    public ArrayList<String> Read() throws SQLException {
        Connection c = DriverManager.getConnection(
                "jdbc:mysql://" + dataBase.dbDomain + "/" +dataBase.dbName,
                dataBase.username,
                dataBase.password);
        return this.contents;
    }

}
