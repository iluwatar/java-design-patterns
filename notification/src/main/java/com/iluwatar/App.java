package com.iluwatar;

import java.time.LocalDate;

/**
 * The notification pattern captures information passed between layers, validates the information, and returns
 * any errors to the presentation layer if needed.
 *
 * <p>In this code, this pattern is implemented through the example of a form being submitted to register
 * a worker. The worker inputs their name, occupation, and date of birth to the RegisterWorkerForm (which acts
 * as our presentation layer), and passes it to the RegisterWorker class (our domain layer) which validates it.
 * Any errors caught by the domain layer are then passed back to the presentation layer through the
 * RegisterWorkerDto.</p>
 */
public class App {

  private static final String NAME = "";
  private static final String OCCUPATION = "";
  private static final LocalDate DATE_OF_BIRTH = LocalDate.of(2016, 7, 13);

  public static void main(String[] args) {
    var form = new RegisterWorkerForm(NAME, OCCUPATION, DATE_OF_BIRTH);
    form.submit();
  }

}
