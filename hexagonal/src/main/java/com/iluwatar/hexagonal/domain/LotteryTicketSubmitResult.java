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
package com.iluwatar.hexagonal.domain;

import java.util.EnumSet;

/**
 * 
 * Immutable value object for passing lottery ticket submit results.
 *
 */
public class LotteryTicketSubmitResult {

  /**
   * 
   * Indicates if the submit was successful
   *
   */
  public enum Result {OK, ERROR};
  
  /**
   * 
   * Indicates the fields in the lottery ticket that have errors.
   *
   */
  public enum Fields {EMAIL, BANK_ACCOUNT, PHONE, NUMBERS};

  private final Result result;
  
  private final EnumSet<Fields> fields;

  /**
   * Constructor.
   */
  public LotteryTicketSubmitResult(Result submitResult) {
    result = submitResult;
    fields = EnumSet.noneOf(Fields.class);
  }
  
  /**
   * Constructor.
   */
  public LotteryTicketSubmitResult(Result submitResult, EnumSet<Fields> errorFields) {
    result = submitResult;
    fields = EnumSet.copyOf(errorFields);
  }

  /**
   * @return submit result
   */
  public Result getResult() {
    return result;
  }
  
  /**
   * @return fields that have errors
   */
  public EnumSet<Fields> getErrorFields() {
    return EnumSet.copyOf(fields);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((fields == null) ? 0 : fields.hashCode());
    result = prime * result + ((this.result == null) ? 0 : this.result.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    LotteryTicketSubmitResult other = (LotteryTicketSubmitResult) obj;
    if (fields == null) {
      if (other.fields != null) {
        return false;
      }
    } else if (!fields.equals(other.fields)) {
      return false;
    }
    if (result != other.result) {
      return false;
    }
    return true;
  }
}
