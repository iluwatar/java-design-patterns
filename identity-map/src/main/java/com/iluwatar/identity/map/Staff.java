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
 * the class for staff.
 */
public final class Staff {

  /**
   * staff's first name.
   */
  private String firstName;

  /**
   * staff's last name.
   */
  private String lastName;

  /**
   * staff's ID and also the key value in the staff database.
   */
  private String staffID;

  /**
   * get first name.
   *
   * @return first name.
   */
  protected String getFirstName() {
    return firstName;
  }

  /**
   * get last name.
   *
   * @return last name.
   */
  protected String getLastName() {
    return lastName;
  }

  /**
   * get staff ID.
   *
   * @return the id of the staff.
   */
  protected String getStaffID() {
    return staffID;
  }

  /**
   * the hidden constructor.
   *
   * @param firstNameParam the staff's first name.
   * @param lastNameParam  the staff's last name.
   * @param staffIdParam   the staff's ID.
   */
  private Staff(final String firstNameParam, final String lastNameParam,
                final String staffIdParam) {
    this.firstName = firstNameParam;
    this.lastName = lastNameParam;
    this.staffID = staffIdParam;
  }

  /**
   * create a staff object and returns it.
   *
   * @param firstName the staff's first name.
   * @param lastName  the staff's last name.
   * @param staffID   the staff's ID.
   * @return the staff object.
   */
  public static Staff createStaff(final String firstName, final String lastName,
                                  final String staffID) {
    return new Staff(firstName, lastName, staffID);
  }

  /**
   * overrides the toString method.
   *
   * @return the formatted string.
   */
  public String toString() {
    return String.format("My first name is %s, last name is %s, ID is %s.",
            getFirstName(), getLastName(), getStaffID());
  }
}
