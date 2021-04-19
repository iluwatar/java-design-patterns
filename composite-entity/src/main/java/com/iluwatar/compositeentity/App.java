package com.iluwatar.compositeentity;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;


/**
 * Composite entity is a Java EE Software design pattern and it is used to model, represent, and
 * manage a set of interrelated persistent objects rather than representing them as individual
 * fine-grained entity beans, and also a composite entity bean represents a graph of objects.
 */
@Slf4j
public class App {


  /**
   * An instance that a console manages two related objects.
   */
  public App(String message, String signal) {
    var console = new CompositeEntity();
    console.init();
    console.setData(message, signal);
    Arrays.stream(console.getData()).forEach(LOGGER::info);
    console.setData("Danger", "Red Light");
    Arrays.stream(console.getData()).forEach(LOGGER::info);
  }

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {

    new App("No Danger", "Green Light");

  }
}
