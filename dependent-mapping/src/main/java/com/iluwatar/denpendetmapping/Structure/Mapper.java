/*
 * This project is licensed under the MIT license.
 * Module model-view-viewmodel is using ZK framework
 * licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
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
package com.iluwatar.denpendetmapping.Structure;

import java.sql.SQLException;

/**
 * Mapper which depend on the master class and dependent class,
 * and connect them with the database.
 */
public interface Mapper {

  /**
   * Show the data from the database.
   *
   * @return the data in database.
   */
  String findstatement();

  /**
   * Updata the specific master instance and its dependent instances.
   *
   * @param arg the master instance.
   * @throws SQLException the exception of SQL
   */
  void update(MasterObj arg) throws SQLException;

  /**
   * Updata the dependent instances of the specific master instance.
   *
   * @param arg the master instance.
   * @throws SQLException the exception of SQL
   */
  void updateDepObjs(MasterObj arg) throws SQLException;

  /**
   * Insert specific a dependent instance into a master instance in database.
   *
   * @param dependentObj the specific dependent instance.
   * @param seq          the sequence.
   * @param masterObj    the specific master instance
   * @throws SQLException the exception of SQL
   */
  void insertDepObj(DependentObj dependentObj,
                    int seq, MasterObj masterObj)
    throws SQLException;

}
