/**
 * The MIT License
 * Copyright (c) 2014 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iluwatar.indextable;

import static com.iluwatar.indextable.App.password;
import static com.iluwatar.indextable.App.username;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Index implements Sql, creating database "students", and the following tables:
 * factTable, indexTable1, indexTable2 and compTable and assigns
 * indexes to respective columns
 * @author Ayush
 */
public class Index implements Sql {

  @Override
  public void sqlImplementation() throws Exception {
    try {
      Class.forName("com.mysql.jdbc.Driver");
      Connection con = DriverManager.getConnection("jdbc:mysql://localhost/",username,password);
      System.out.println("Connecting to database...");
      
      Statement st = con.createStatement();
      System.out.println("Creating statement...");
      
      ResultSet rs;
      st.execute("drop database if exists students");
      st.execute("create database students");
      System.out.println("Creating database...");
      
      st.execute("use students");
      String tableDetails = "(first_name varchar(30) NOT NULL,"
                           + "last_name varchar(30) NOT NULL,"
                           + "town varchar(30) NOT NULL,"
                           + "score int,"
                           + "id int auto_increment,";
      String tableExtra = "PRIMARY KEY(id))";
      st.execute("create table factTable" + tableDetails + tableExtra);
      String insert = getData();
      st.execute(insert);
      System.out.println("Fact Table initialised");
      
      createTables(st);
      
      rs = st.executeQuery("select * from factTable");
      System.out.println("\nTable data:\n First Name | Last Name | Town | Score | ID");
      while (rs.next()) {
        String data = rs.getString(1)
                + " | " + rs.getString(2)
                + " | " + rs.getString(3)
                + " | " + rs.getString(4)
                + " | " + rs.getString(5);
        System.out.println(data);
      }
      rs.close();
      st.close();
      con.close();
    } catch (Exception e) {
      throw e;
    }
  }


  @Override
  public void createTables(Statement st) {
      
    try {
      st.execute("create table indexTable1 select score,id from factTable");
      st.execute("alter table indexTable1 add index sc(score)");
      System.out.println("\nCreating first index table");
      
      st.execute("create table indexTable2 select last_name,id from factTable");
      st.execute("alter table indexTable2 add index sc(last_name)");
      System.out.println("Creating second index table");
      
      st.execute("create table compTable select last_name,score,id from factTable");
      st.execute("alter table compTable add index sc(last_name),add index sc2(score)");
      System.out.println("Creating composite key index table");
    } catch (Exception e) {
      try {
        throw e;
      } catch (Exception ex) {
        Logger.getLogger(Index.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }
   
  @Override
  public String getData() {
    String data = "insert into factTable values";
    String []firstName = {"Ayush","Shivangi","Eshan","Satya","Apoorva","Ashna","Sonam","Emma","Anne","Ellen"};
    String []lastName = {"Kumar","Ranjan","Indoliya","Prakash","Kumar","Kumari","Kumari","Geller","Rayman","Seyfield"};
    String []town = {"Ropar","Kanpur","Ropar","Guwahati","Guwahati","Patna","Patna","California","Boston","London"};
    int []score = {98,96,98,92,94,97,97,95,95,99};
    for (int i = 0;i < 10;++i) {
      data = data + "('" + firstName[i] + "', '" + lastName[i] + "', '" + town[i] + "', " + score[i] + ",NULL),";
    }
    data = data.substring(0,data.length() - 1);
    return data;
  }  
}
