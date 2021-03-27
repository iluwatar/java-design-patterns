/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.iluwatar.hexagonal.service;

import com.iluwatar.hexagonal.banking.WireTransfers;
import com.iluwatar.hexagonal.domain.LotteryNumbers;
import com.iluwatar.hexagonal.domain.LotteryService;
import com.iluwatar.hexagonal.domain.LotteryTicket;
import com.iluwatar.hexagonal.domain.LotteryTicketCheckResult;
import com.iluwatar.hexagonal.domain.LotteryTicketId;
import com.iluwatar.hexagonal.domain.PlayerDetails;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import org.slf4j.Logger;

/**
 * Console implementation for lottery console service.
 */
public class LotteryConsoleServiceImpl implements LotteryConsoleService {

  private final Logger logger;

  /**
   * Constructor.
   */
  public LotteryConsoleServiceImpl(Logger logger) {
    this.logger = logger;
  }

  @Override
  public void checkTicket(LotteryService service, Scanner scanner) {
    logger.info("What is the ID of the lottery ticket?");
    var id = readString(scanner);
    logger.info("Give the 4 comma separated winning numbers?");
    var numbers = readString(scanner);
    try {
      var winningNumbers = Arrays.stream(numbers.split(","))
          .map(Integer::parseInt)
          .limit(4)
          .collect(Collectors.toSet());

      final var lotteryTicketId = new LotteryTicketId(Integer.parseInt(id));
      final var lotteryNumbers = LotteryNumbers.create(winningNumbers);
      var result = service.checkTicketForPrize(lotteryTicketId, lotteryNumbers);

      if (result.getResult().equals(LotteryTicketCheckResult.CheckResult.WIN_PRIZE)) {
        logger.info("Congratulations! The lottery ticket has won!");
      } else if (result.getResult().equals(LotteryTicketCheckResult.CheckResult.NO_PRIZE)) {
        logger.info("Unfortunately the lottery ticket did not win.");
      } else {
        logger.info("Such lottery ticket has not been submitted.");
      }
    } catch (Exception e) {
      logger.info("Failed checking the lottery ticket - please try again.");
    }
  }

  @Override
  public void submitTicket(LotteryService service, Scanner scanner) {
    logger.info("What is your email address?");
    var email = readString(scanner);
    logger.info("What is your bank account number?");
    var account = readString(scanner);
    logger.info("What is your phone number?");
    var phone = readString(scanner);
    var details = new PlayerDetails(email, account, phone);
    logger.info("Give 4 comma separated lottery numbers?");
    var numbers = readString(scanner);
    try {
      var chosen = Arrays.stream(numbers.split(","))
          .map(Integer::parseInt)
          .collect(Collectors.toSet());
      var lotteryNumbers = LotteryNumbers.create(chosen);
      var lotteryTicket = new LotteryTicket(new LotteryTicketId(), details, lotteryNumbers);
      service.submitTicket(lotteryTicket).ifPresentOrElse(
          (id) -> logger.info("Submitted lottery ticket with id: {}", id),
          () -> logger.info("Failed submitting lottery ticket - please try again.")
      );
    } catch (Exception e) {
      logger.info("Failed submitting lottery ticket - please try again.");
    }
  }

  @Override
  public void addFundsToLotteryAccount(WireTransfers bank, Scanner scanner) {
    logger.info("What is the account number?");
    var account = readString(scanner);
    logger.info("How many credits do you want to deposit?");
    var amount = readString(scanner);
    bank.setFunds(account, Integer.parseInt(amount));
    logger.info("The account {} now has {} credits.", account, bank.getFunds(account));
  }

  @Override
  public void queryLotteryAccountFunds(WireTransfers bank, Scanner scanner) {
    logger.info("What is the account number?");
    var account = readString(scanner);
    logger.info("The account {} has {} credits.", account, bank.getFunds(account));
  }

  private String readString(Scanner scanner) {
    logger.info("> ");
    return scanner.next();
  }
}
