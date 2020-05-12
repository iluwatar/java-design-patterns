/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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

package com.iluwatar.microkernel.models;

import java.time.LocalDate;

/**
 * This class represents transactions.
 * A transaction is an economic event with a third
 * party that is recorded in an organization's accounting system.
 * Such a transaction must be measurable in money.
 */
public class Transaction {

  private String description;
  private int relatedAccountNumber;
  private LocalDate date;
  private double value;
  private FlowingType flowingtype;

  /**
   * Use this constructor to create an Transaction with all details.
   * @param description   as description of the event
   * @param date          as the exact date of the event
   * @param value         as value change
   * @param flowingtype   as type of change
   */
  public Transaction(String description, LocalDate date, double value, FlowingType flowingtype) {
    this.description = description;
    this.date = date;
    this.value = value;
    this.flowingtype = flowingtype;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public double getValue() {
    return value;
  }

  public void setValue(double value) {
    this.value = value;
  }

  public FlowingType getFlowingtype() {
    return flowingtype;
  }

  public void setFlowingtype(FlowingType flowingtype) {
    this.flowingtype = flowingtype;
  }

  public int getRelatedAccountNumber() {
    return relatedAccountNumber;
  }

  public void setRelatedAccountNumber(int relatedAccountNumber) {
    this.relatedAccountNumber = relatedAccountNumber;
  }
}
