package com.iluwatar.presentation;

import lombok.extern.slf4j.Slf4j;

/**
 * a class used to start this demo.
 */
@Slf4j
public class App {
  public static void main(String[] args) {
    var view = new View();
    view.createView();
  }
}

