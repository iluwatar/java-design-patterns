package com.iluwatar.hexagonal.service;

import com.iluwatar.hexagonal.banking.WireTransfers;
import com.iluwatar.hexagonal.domain.LotteryService;

import java.util.Scanner;


/**
 * Console interface for lottery service
 */
public interface LotteryConsoleService {

  void checkTicket(LotteryService service, Scanner scanner);

  /**
  * Submit lottery ticket to participate in the lottery
  */
  void submitTicket(LotteryService service, Scanner scanner);

  /**
  * Add funds to lottery account
  */
  void addFundsToLotteryAccount(WireTransfers bank, Scanner scanner);


  /**
  * Recovery funds from lottery account
  */
  void queryLotteryAccountFunds(WireTransfers bank, Scanner scanner);

}
