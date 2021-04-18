package com.iluwatar.compositeentity;

import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Composite entity is a Java EE Software design pattern and it is used to model, represent, and
 * manage a set of interrelated persistent objects rather than representing them as individual
 * fine-grained entity beans, and also a composite entity bean represents a graph of objects.
 */
public class App {

  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {
    var console = new CompositeEntity();
    console.setData("No Danger", "Green Light");
    Arrays.stream(console.getData()).forEach(LOGGER::info);

    console.setData("Danger", "Red Light");
    Arrays.stream(console.getData()).forEach(LOGGER::info);
  }
}
