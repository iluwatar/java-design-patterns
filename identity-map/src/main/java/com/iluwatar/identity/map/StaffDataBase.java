/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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

package com.iluwatar.identity.map;

/**
 * the database simulation class.
 */
public class StaffDataBase {

  /**
   * the staff array used to simulate a staff database.
   */
  private Staff[] staffData = {Staff.createStaff("Jacob", "Dave", "s1"),
                  Staff.createStaff("Evans", "Steve", "s2"),
                  Staff.createStaff("Jason", "Rogers", "s3")};

  /**
   * find the staff according id.
   *
   * @param id staff id.
   * @return the staff object.
   */
  public Staff finder(final String id) {
    Staff staff = IdentityMapUtility.getStaff(id);
    if (staff == null) {

      staff = staffData[id.charAt(1) - '0' - 1];
      IdentityMapUtility.addStaff(staff);

    }
    return staff;
  }

}
