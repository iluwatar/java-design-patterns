package com.iluwatar.choreography;

import com.iluwatar.choreography.events.Event;

public class Util {

  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_BG_RED = "\u001B[41m";
  public static final String ANSI_BG_GREEN = "\u001B[42m";
  public static final String ANSI_BG_BLUE = "\u001B[44m";

  public static void performAction(Event e, String s) {
    System.out.println(e.getPrettySagaId() + s);
  }
}