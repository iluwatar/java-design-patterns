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
 * Performance implements Query and compares number of rows scanned for a query
 * in factTable to number of rows scanned in indexTables. As we'll see number of
 * rows scanned in indexTables are significantly less than that of factTable.
 * @author Ayush
 */
public class Performance implements Query {

  @Override
  public void runQueries() {
    try {
      Class.forName("com.mysql.jdbc.Driver");
      Connection con = DriverManager.getConnection("jdbc:mysql://localhost/",username,password);    
      Statement st = con.createStatement();
      ResultSet rs;
      st.execute("use students");
      System.out.println("\nQuery 1: SELECT score FROM factTable WHERE score=99");
      rs = st.executeQuery("explain select score from factTable where score=99");
      while (rs.next()) {
        System.out.print("Rows scanned for Fact Table= " + rs.getString(10));
      }
      
      rs = st.executeQuery("explain select score from indexTable1 where score=99");
      while (rs.next()) {
        System.out.println(" | Rows scanned for Index Table= " + rs.getString(10));
      }
      
      System.out.println("\nQuery 2: SELECT score FROM factTable WHERE last_name='Kumar' AND score>92");
      rs = st.executeQuery("explain select score from factTable where last_name='Kumar' and score>92");
      while (rs.next()) {
        System.out.print("Rows scanned for Fact Table= " + rs.getString(10));
      }  
      
      rs = st.executeQuery("explain select score from compTable where last_name='Kumar' and score>92");
      while (rs.next()) {
        System.out.println(" | Rows scanned for Composite key Table= " + rs.getString(10));
      }
      st.execute("drop database students");
      rs.close();
      st.close();
      con.close();
    } catch (Exception e) {
      try {
        throw e;
      } catch (Exception ex) {
        Logger.getLogger(Index.class.getName()).log(Level.SEVERE, null, ex);
      }
    }      
  }
    
}
