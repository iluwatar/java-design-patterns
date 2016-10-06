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
package com.iluwatar.indextable;

/**
 * Index Table pattern create indexes over the fields in data stores that are
 * frequently referenced by query criteria. This pattern can improve query
 * performance by allowing applications to more quickly locate the data to
 * retrieve from a data store. This is achieved by creating secondary indexes.
 * Many relational database management systems support secondary indexes.
 * The items in a secondary index are typically sorted by the value of the 
 * secondary keys to enable fast lookup of data. These indexes are usually
 * maintained automatically by the database management system. However, although
 * secondary indexes are a common feature of relational systems, most NoSQL data
 * stores used by cloud applications do not provide an equivalent feature.
 */

public class App {
   
  public static String username = "root";
  public static String password = "0000";
    /**
   * Program entry point
   * 
   * @param args command line args
   */
  
  public static void main(String[] args) throws Exception {
    Sql sql = new Index();
    sql.sqlImplementation();
    Query query = new Performance();
    query.runQueries();
  }
 
    
}
