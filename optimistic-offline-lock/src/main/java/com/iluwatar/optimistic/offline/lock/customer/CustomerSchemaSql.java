/*
*The MIT License
*Copyright © 2014-2021 Ilkka Seppälä
*
*Permission is hereby granted, free of charge, to any person obtaining a copy
*of this software and associated documentation files (the "Software"), to deal
*in the Software without restriction, including without limitation the rights
*to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
*copies of the Software, and to permit persons to whom the Software is
*furnished to do so, subject to the following conditions:
*
*The above copyright notice and this permission notice shall be included in
*all copies or substantial portions of the Software.
*
*THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
*IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
*FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
*AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
*LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
*OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
*THE SOFTWARE.
*/

/*
 *The MIT License
 *Copyright © 2014-2021 Ilkka Seppälä
 *
 *Permission is hereby granted, free of charge, to any person obtaining a copy
 *of this software and associated documentation files (the "Software"), to deal
 *in the Software without restriction, including without limitation the rights
 *to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *copies of the Software, and to permit persons to whom the Software is
 *furnished to do so, subject to the following conditions:
 *
 *The above copyright notice and this permission notice shall be included in
 *all copies or substantial portions of the Software.
 *
 *THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *THE SOFTWARE.
 */

package com.iluwatar.optimistic.offline.lock.customer;

/** Customer Schema SQL Class. */
public final class CustomerSchemaSql {

  public static final String CREATE_SCHEMA_SQL =
      "CREATE TABLE CUSTOMERS ("
          + "id LONG NOT NULL, "
          + "first_name VARCHAR(60), "
          + "last_name VARCHAR(100), "
          + "modified_by VARCHAR(100), "
          + "modified TIMESTAMP, "
          + "version INTEGER, "
          + "PRIMARY KEY ( id ));";

  public static final String POPULATE_DB =
      "INSERT INTO CUSTOMERS VALUES (1, 'Hawa', 'Parra', 'main', '2022-05-20', 1);"
          + "INSERT INTO CUSTOMERS VALUES (2, 'Rose', 'Squires', 'main', '2022-05-20', 2);"
          + "INSERT INTO CUSTOMERS VALUES (3, 'Hammad', 'Wright', 'main', '2022-05-21', 1);";

  public static final String DELETE_SCHEMA_SQL = "DROP TABLE IF EXISTS CUSTOMERS";
}
