package com.iluwatar.choreography.events;

import static com.iluwatar.choreography.Util.ANSI_BG_BLUE;
import static com.iluwatar.choreography.Util.ANSI_BG_GREEN;
import static com.iluwatar.choreography.Util.ANSI_BG_RED;
import static com.iluwatar.choreography.Util.ANSI_RESET;

public class Event {

  private final int sagaId;

  public Event(int sagaId) {
    this.sagaId = sagaId;
  }

  public int getSagaId() {
    return sagaId;
  }

  /**
   * Returns a string that represents the current saga id.
   *
   * @return colored string that represents the saga id.
   */
  public String getPrettySagaId() {
    switch (getSagaId()) {
      case 0:
        return (ANSI_BG_RED + "Saga 1 " + ANSI_RESET + "| ");
      case 1:
        return (ANSI_BG_GREEN + "Saga 2 " + ANSI_RESET + "| ");
      case 2:
        return (ANSI_BG_BLUE + "Saga 3 " + ANSI_RESET + "| ");
      default:
        return "Unknown| ";
    }
  }
}



