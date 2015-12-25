package com.iluwatar.front.controller;

/**
 * 
 * Command for catapults.
 *
 */
public class CatapultCommand implements Command {

  @Override
  public void process() {
    new CatapultView().display();
  }
}
