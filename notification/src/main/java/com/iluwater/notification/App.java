package com.iluwater.notification;

import java.time.LocalDate;

/**
 *
 */
public class App {
  private static final String NAME = "";
  private static final String OCCUPATION = "";
  private static final LocalDate DATE_OF_BIRTH = LocalDate.of(2016, 7, 13);

  private App() {
  }

  public static void main(String[] args) {
    RegisterWorkerForm form = new RegisterWorkerForm(NAME, OCCUPATION, DATE_OF_BIRTH);
    form.submit();
  }
}
