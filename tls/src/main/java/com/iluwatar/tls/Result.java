/*
 * Fiducia IT AG, All rights reserved. Use is subject to license terms.
 */

package com.iluwatar.tls;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Result object that will be returned by the Callable {@link DateFormatCallable} 
 * used in {@link App}
 *
 * @author Thomas Bauer, 2017 
 */
public class Result {
  // A list to collect the date values created in one thread
  private List<Date> dateList = new ArrayList<Date>();

  // A list to collect Exceptions thrown in one threads (should be none in
  // this example)
  private List<String> exceptionList = new ArrayList<String>();
  
  /**
   * 
   * @return List of date values collected within an thread execution
   */
  public List<Date> getDateList() {
    return dateList;
  }

  /**
   * 
   * @return List of exceptions thrown within an thread execution
   */
  public List<String> getExceptionList() {
    return exceptionList;
  }
}
