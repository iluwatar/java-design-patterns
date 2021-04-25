package com.iluwatar.presentation;

import lombok.extern.slf4j.Slf4j;

/**
 * a class used to start this demo.
 */
@Slf4j
class App {
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

