package com.iluwatar.hexagonal.domain;

import java.util.Arrays;
import java.util.HashSet;

/**
 * 
 * Utilities for lottery tests
 *
 */
public class LotteryTestUtils {

  public static LotteryTicket createLotteryTicket() {
    PlayerDetails details = PlayerDetails.create("foo@bar.com", "12231-213132", "+99324554");
    LotteryNumbers numbers = LotteryNumbers.create(new HashSet<>(Arrays.asList(1, 2, 3, 4)));
    return LotteryTicket.create(details, numbers);
  }
}
