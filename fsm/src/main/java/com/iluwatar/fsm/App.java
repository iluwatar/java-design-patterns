package com.iluwatar.fsm;

/**
 * Test locked to unlocked, unlocked to locked, locked to failed, failed to fixed.
 */
public class App {
  /**
   * Main method.
   *
   * @param args runtime arguments
   */
  public static void main(String[] args) {
    CoinMachine coinMachine = new CoinMachine();
    System.out.println(coinMachine.currentState());
    coinMachine.coin();
    System.out.println(coinMachine.currentState());
    coinMachine.pass();
    System.out.println(coinMachine.currentState());
    coinMachine.failed();
    System.out.println(coinMachine.currentState());
    coinMachine.fixed();
    System.out.println(coinMachine.currentState());
  }
}
