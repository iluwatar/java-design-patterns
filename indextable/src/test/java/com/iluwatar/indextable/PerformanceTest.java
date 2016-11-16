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
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author Ayush
 */
public class PerformanceTest {
    
  private static Connection con;
  private static Statement st;
    
  public PerformanceTest() {
  }
    
  @BeforeClass
  public static void setUpClass() throws Exception {
    try {
      Class.forName("com.mysql.jdbc.Driver");
      con = DriverManager.getConnection("jdbc:mysql://localhost/",username,password);
      st = con.createStatement();
    } catch (ClassNotFoundException | SQLException e) {
      throw e;
    }
  }
    
  @AfterClass
  public static void tearDownClass() throws Exception {
    try {
      st.execute("drop database students");
      st.close();
      con.close();
    } catch (Exception e) {
      throw e;
    }
  }
    
  @Before
  public void setUp() {
  }
    
  @After
  public void tearDown() {
  }

  /**
   * Test of runQueries method, of class Performance.
   */

  @Test
  public void testRunQueries() throws Exception {
    System.out.println("Testing runQueries: ");
    Performance instance = new Performance();
    try {
      ResultSet rs;
      st.execute("use students");
      String result = "";
      rs = st.executeQuery("explain select score from indexTable1 where score=99");
      while (rs.next()) {
        result = rs.getString(10);
      }
      assertEquals("1", result);
      rs = st.executeQuery("explain select score from compTable where last_name='Kumar' and score>92");
      while (rs.next()) {
        result = rs.getString(10);
      }
      assertEquals("2", result);
    } catch (Exception e) {
      throw e;
    }
    
  }
    
}
