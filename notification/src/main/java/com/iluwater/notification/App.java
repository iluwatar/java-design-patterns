package com.iluwater.notification;

import java.time.LocalDate;

/**
 * You can think of this application as filling in the form with information on a worker.
 * This could include their name, occupation, and date of birth as an example.
 * This is then submitted by the form for processing in the domain layer of our application.
 */
public class App {
  /**
   * Variables to be submitted in the form.
   */
  private static final String NAME = "";
  private static final String OCCUPATION = "";
  private static final LocalDate DATE_OF_BIRTH = LocalDate.of(2016, 7, 13);

  private App() {
  }

  /**
   * Program entry point.
   *
   * @param args command line arguments, not used here
   */
  public static void main(String[] args) {
    RegisterWorkerForm form = new RegisterWorkerForm(NAME, OCCUPATION, DATE_OF_BIRTH);
    form.submit();
  }
}
