/**
 * The MIT License
 * Copyright (c) 2014 Ilkka Seppälä
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

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.iluwatar.hexagonal.banking.WireTransfers;
import com.iluwatar.hexagonal.domain.LotteryNumbers;
import com.iluwatar.hexagonal.domain.LotteryService;
import com.iluwatar.hexagonal.domain.LotteryTicket;
import com.iluwatar.hexagonal.domain.LotteryTicketCheckResult;
import com.iluwatar.hexagonal.domain.LotteryTicketId;
import com.iluwatar.hexagonal.domain.PlayerDetails;
import com.iluwatar.hexagonal.module.LotteryModule;
import com.iluwatar.hexagonal.mongo.MongoConnectionPropertiesLoader;

import java.util.HashSet;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

/**
 * Console interface for lottery players
 */
public class ConsoleLottery {


  /**
   * Program entry point
   */
  public static void main(String[] args) {
    MongoConnectionPropertiesLoader.load();
    Injector injector = Guice.createInjector(new LotteryModule());
    LotteryService service = injector.getInstance(LotteryService.class);
    WireTransfers bank = injector.getInstance(WireTransfers.class);
    Scanner scanner = new Scanner(System.in);
    boolean exit = false;
    while (!exit) {
      printMainMenu();
      String cmd = readString(scanner);
      if (cmd.equals("1")) {
        System.out.println("What is the account number?");
        String account = readString(scanner);
        System.out.println(String.format("The account %s has %d credits.", account, bank.getFunds(account)));
      } else if (cmd.equals("2")) {
        System.out.println("What is the account number?");
        String account = readString(scanner);
        System.out.println("How many credits do you want to deposit?");
        String amount = readString(scanner);
        bank.setFunds(account, Integer.parseInt(amount));
        System.out.println(String.format("The account %s now has %d credits.", account, bank.getFunds(account)));
      } else if (cmd.equals("3")) {
        System.out.println("What is your email address?");
        String email = readString(scanner);
        System.out.println("What is your bank account number?");
        String account = readString(scanner);
        System.out.println("What is your phone number?");
        String phone = readString(scanner);
        PlayerDetails details = new PlayerDetails(email, account, phone);
        System.out.println("Give 4 comma separated lottery numbers?");
        String numbers = readString(scanner);
        try {
          String[] parts = numbers.split(",");
          Set<Integer> chosen = new HashSet<>();
          for (int i = 0; i < 4; i++) {
            chosen.add(Integer.parseInt(parts[i]));
          }
          LotteryNumbers lotteryNumbers = LotteryNumbers.create(chosen);
          LotteryTicket lotteryTicket = new LotteryTicket(new LotteryTicketId(), details, lotteryNumbers);
          Optional<LotteryTicketId> id = service.submitTicket(lotteryTicket);
          if (id.isPresent()) {
            System.out.println("Submitted lottery ticket with id: " + id.get());
          } else {
            System.out.println("Failed submitting lottery ticket - please try again.");
          }
        } catch (Exception e) {
          System.out.println("Failed submitting lottery ticket - please try again.");
        }
      } else if (cmd.equals("4")) {
        System.out.println("What is the ID of the lottery ticket?");
        String id = readString(scanner);
        System.out.println("Give the 4 comma separated winning numbers?");
        String numbers = readString(scanner);
        try {
          String[] parts = numbers.split(",");
          Set<Integer> winningNumbers = new HashSet<>();
          for (int i = 0; i < 4; i++) {
            winningNumbers.add(Integer.parseInt(parts[i]));
          }
          LotteryTicketCheckResult result = service.checkTicketForPrize(
              new LotteryTicketId(Integer.parseInt(id)), LotteryNumbers.create(winningNumbers));
          if (result.getResult().equals(LotteryTicketCheckResult.CheckResult.WIN_PRIZE)) {
            System.out.println("Congratulations! The lottery ticket has won!");
          } else if (result.getResult().equals(LotteryTicketCheckResult.CheckResult.NO_PRIZE)) {
            System.out.println("Unfortunately the lottery ticket did not win.");
          } else {
            System.out.println("Such lottery ticket has not been submitted.");
          }
        } catch (Exception e) {
          System.out.println("Failed checking the lottery ticket - please try again.");
        }
      } else if (cmd.equals("5")) {
        exit = true;
      } else {
        System.out.println("Unknown command");
      }
    }
  }

  private static void printMainMenu() {
    System.out.println("");
    System.out.println("### Lottery Service Console ###");
    System.out.println("(1) Query lottery account funds");
    System.out.println("(2) Add funds to lottery account");
    System.out.println("(3) Submit ticket");
    System.out.println("(4) Check ticket");
    System.out.println("(5) Exit");
  }

  private static String readString(Scanner scanner) {
    System.out.print("> ");
    String cmd = scanner.next();
    return cmd;
  }
}
