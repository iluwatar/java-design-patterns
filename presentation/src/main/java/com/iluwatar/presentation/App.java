package com.iluwatar.presentation;

import lombok.extern.slf4j.Slf4j;

/**
 * The Presentation model pattern is used to divide the presentation and controlling.
 * This demo is a used to information of some albums with GUI.
 */
@Slf4j
public final class App {
  /**
   * the constructor.
   */
  private App() {
  }

  /**
   * main method.
   *
   * @param args args
   */
  public static void main(final String[] args) {
    var view = new View();
    view.createView();
  }
}

