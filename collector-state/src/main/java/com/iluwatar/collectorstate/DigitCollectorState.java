package com.iluwatar.collectorstate;


/**
 * Collector-state pattern is to manage sequence of events of same type.
 * For example, a Call object needs to collect all
 * the digits to perform call routing. This will involve activities like
 * a timer start before each digit, digit collection, timer stop at each
 * digit collection, timeout on complete digit collection and call routing
 * on complete digit collection.
 */

public final class DigitCollectorState {
  int receivedDigits = 0;               // Count of received digits
  final int expectedDigits;         // Total number of expected digits
  final int minimumDigits;  // Minimum number of expected digits
  String description;

  /**
   * constructor.

   * @param expectedDigits expected number of digits in this state
   * @param minimumDigits minimum number of digits in this state
   */
  public DigitCollectorState(int expectedDigits, int minimumDigits) {
    this.expectedDigits = expectedDigits;
    this.minimumDigits = minimumDigits;
    this.description = String.format("Expected Digits: %d,Minimum Digits: %d",
            expectedDigits, minimumDigits);
  }

  String getDescription() {
    return description;
  }

  /**
   * Handle the received Digit message.

   * @param m State Machine
   * @param message Message consisted of digits
   */
  public void onDigit(StateMachine m, DigitMsg message) {
    // Save the digit and increment the received digit counter
    m.digits[receivedDigits] = message.getDigit();
    this.receivedDigits++;
    // Now check if the message explicitly signals the last digit, or if
    // expected number of digits have been received.
    if (message.isLastDigit() || (receivedDigits == expectedDigits)) {
      // Digit collection has been completed, so proceed to call routing
      m.changeState(State.CallRoutingState);
    } else {
      // More digits are expected, continue
    }
  }

  void restartDigitTimer() {
    receivedDigits = 0;
  }

  // Handle Digit Timeout
  void onDigitTimeout(StateMachine m) {
    if (receivedDigits < minimumDigits) {
      // If too few digits have been received, the call moves
      // to the state handling partial dialing
      m.changeState(State.PartialDialingState);
    } else {
      // If more than minimum digits are received and timeout
      // takes place, assume that digit collection has been
      // completed, so proceed with call routing.
      m.changeState(State.CallRoutingState);
    }
  }
}