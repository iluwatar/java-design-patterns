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
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ayush
 */
public class IndexTest {
    
  private static Connection con;
  private static Statement st;
  
  @BeforeClass
  public static void setUpClass() throws Exception {
    try {
      Class.forName("com.mysql.jdbc.Driver");
      con = DriverManager.getConnection("jdbc:mysql://localhost/",username,password);
      st = con.createStatement();
    } catch (Exception e) {
      throw e;
    }
  }
    
  @AfterClass
  public static void tearDownClass() throws Exception {
    try {
      st.close();
      con.close();
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * Test of createTables method, of class Index.
   */
  
  @Test
  public void testCreateTables() throws SQLException, Exception {
    System.out.println("Testing createTables: ");
    Index instance = new Index();
    instance.sqlImplementation();
    st.execute("use students");
    ResultSet rs = st.executeQuery("describe indexTable1");
    String key = null;
    while (rs.next()) {
      key = rs.getString(4);
      break;
    }
    assertEquals("MUL", key);
    rs = st.executeQuery("describe indexTable2");
    while (rs.next()) {
      key = rs.getString(4);
      break;
    }
    assertEquals("MUL", key);
    rs = st.executeQuery("describe compTable");
    key = "";
    while (rs.next()) {
      key += rs.getString(4) + " ";
    }
    assertEquals("MUL MUL  ", key);
  }

  /**
   * Test of getData method, of class Index.
   */
  @Test
  public void testGetData() {
    System.out.println("Testing getData: ");
    Index instance = new Index();
    String expResult = "insert into factTable values"
            + "('Ayush', 'Kumar', 'Ropar', 98,NULL),"
            + "('Shivangi', 'Ranjan', 'Kanpur', 96,NULL),"
            + "('Eshan', 'Indoliya', 'Ropar', 98,NULL),"
            + "('Satya', 'Prakash', 'Guwahati', 92,NULL),"
            + "('Apoorva', 'Kumar', 'Guwahati', 94,NULL),"
            + "('Ashna', 'Kumari', 'Patna', 97,NULL),"
            + "('Sonam', 'Kumari', 'Patna', 97,NULL),"
            + "('Emma', 'Geller', 'California', 95,NULL),"
            + "('Anne', 'Rayman', 'Boston', 95,NULL),"
            + "('Ellen', 'Seyfield', 'London', 99,NULL)";
    String result = instance.getData();
    assertEquals(expResult, result);
  }
    
}
