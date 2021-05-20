package com.iluwatar.collectorstate;

/**
 * class of message which contains digits.
 */
public class DigitMsg {
  int pointer = 0;
  String[] digits = new String[]{"h", "e", "l", "l", "o", "java", "design", "pattern"};

  /**
   * get digit in sequence of the message.

   * @return digit
   */
  public String getDigit() {
    if (pointer == digits.length) {
      return "";
    }
    String result = digits[pointer++];
    return result;
  }

  /**
   * check if the message has been read completely.

   * @return true: has completed
   */
  public boolean isLastDigit() {
    if (pointer == digits.length) {
      return true;
    }
    return false;
  }
}
