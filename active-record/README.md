---
title: Active Record
category: Architectural  
language: en
tags:
 - Data access
---

## Intent
The active record design pattern is a way of accessing data from databases by creating an object which contains the contents of a database row.

## Explanation
The ActiveRecord object is intitialised by an ActiveDatabase object which creates a connection to an exisitng database.

Real-world example
> You want to implement an application which access and update a database from within an OOP context.
> Update the database within a program.

In plain words

> For a system which requires database storage and ability to change its contents too.

Wikipedia says
>The active record pattern is an approach to accessing data in a database. A database table or view is wrapped into a class. Thus, an object instance is tied to a single row in the table. After creation of an object, a new row is added to the table upon save. Any object loaded gets its information from the database. When an object is updated, the corresponding row in the table is also updated. The wrapper class implements accessor methods or properties for each column in the table or view.


**Programmatic Example**

This implementation shows what the Active Record pattern could look like in a generic context. The Active Record makes initialises `contents` and `columns` as lists of strings. A developer can create an `ActiveDatabase` object by entering the configuration details required to start the connection. Assuming there is an exsiting database the developer would then create an `ActiveRecord` object using the `ActiveDatabase` object and the `id` of the row with which they would like to create in an active context. They are able to locate the row which matches this `id` then this can be read or deleted. 

If you are adding in a new row you can just update the `contents` list which would be empty if created with an `id` which doesnt exist yet.

```java
public class ActiveRow {

  String id;
  ActiveDatabase dataBase;
  Connection con;
  String read;
  String delete;
  String write;
  int columnCount;
  ArrayList<String> columns;
  ArrayList<String> contents = new ArrayList<>();

  /**
   * ActiveRow attempts to implement the fundamental ruby on rails 'Active Record' design pattern in
   * Java.
   *
   * @param dataBase A Database object which handles opening the connection.
   * @param id       The unique identifier of a row.
   */
  public ActiveRow(ActiveDatabase dataBase, String id) {
    this.dataBase = dataBase;
    this.id = id;
    initialise();
  }

  /**
   * This initialises the class by creating a connection and populating the column names and other
   * variables which are used in the program.
   */
  @Rowverride
  public void initialise() {
    try {
      con = DriverManager
          .getConnection("jdbc:mysql://" + dataBase.getDbDomain() + "/" + dataBase.getDbName(),
              dataBase.getUsername(), dataBase.getPassword());
    } catch (SQLException e) {
      e.printStackTrace();
    }

    try {
      Statement statement = con.createStatement();
      ResultSet columnSet = statement.executeQuery(
          "SELECT * FROM `" + dataBase.getDbName() + "`.`" + dataBase.getTableName() + "`");
      ArrayList<String> columnNames = new ArrayList<String>();
      ResultSetMetaData rsmd = columnSet.getMetaData();
      columnCount = rsmd.getColumnCount();
      for (int i = 1; i < columnCount + 1; i++) {
        String name = rsmd.getColumnName(i);
        columnNames.add(name);
      }
      this.columns = columnNames;
      read =
          "SELECT * FROM `" + dataBase.getDbName() + "`.`" + dataBase.getTableName() + "` WHERE ID="
              + this.id;
      delete = "DELETE FROM `" + dataBase.getDbName() + "`.`" + dataBase.getTableName() + "`"
          + " WHERE ID = '" + this.id + "';";
      write = "INSERT INTO `" + dataBase.getDbName() + "`.`" + dataBase.getTableName() + "`";
      statement.close();
      columnSet.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }


  }

  /**
   * Writes contents of the active row object to the chosen database.
   */
  @Rowverride
  public void write() {
    StringBuilder query = new StringBuilder();
    query.append(write);
    query.append(" VALUES (");
    for (String con : this.contents) {
      if (contents.indexOf(con) != this.contents.size() - 1) {
        query.append("'").append(con).append("' ,");
      } else {
        query.append("'").append(con).append("' )");
      }
    }

    try {
      PreparedStatement stmt = con.prepareStatement(query.toString());
      stmt.executeUpdate();

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  /**
   * Deletes the current instance of the active row from the database based on the given ID value.
   */
  @Rowverride
  public void delete() {
    if (this.read().equals(new ArrayList<String>())) {
      System.out.println("Row does not exist.");
      return;
    }
    try {
      con.prepareStatement(delete).executeUpdate();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Reads the current active row.
   *
   * @return the current active row contents as Arraylist
   */
  @Rowverride
  public ArrayList<String> read() {
    try {
      Statement statement = con.createStatement();

      ResultSet rowResult = statement.executeQuery(read);
      while (rowResult.next()) {
        for (int col = 1; col <= columnCount; col++) {
          Object value = rowResult.getObject(col);
          if (value != null) {
            contents.add(value.toString());
          }
        }
      }
      rowResult.close();

    } catch (Exception e) {
      e.printStackTrace();
    }

    return this.contents;
  }

}
```


![alt text](./etc/active-record.png "Active Record Traits and Domain")
