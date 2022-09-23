/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
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
package com.iluwatar.hexagonal.administration;

import com.google.inject.Guice;
import com.iluwatar.hexagonal.domain.LotteryAdministration;
import com.iluwatar.hexagonal.domain.LotteryService;
import com.iluwatar.hexagonal.module.LotteryModule;
import com.iluwatar.hexagonal.mongo.MongoConnectionPropertiesLoader;
import com.iluwatar.hexagonal.sampledata.SampleData;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

/**
 * Console interface for lottery administration.
 */
@Slf4j
public class ConsoleAdministration {

  /**
   * Program entry point.
   */
  public static void main(String[] args) {
    MongoConnectionPropertiesLoader.load();
    var injector = Guice.createInjector(new LotteryModule());
    var administration = injector.getInstance(LotteryAdministration.class);
    var service = injector.getInstance(LotteryService.class);
    SampleData.submitTickets(service, 20);
    var consoleAdministration = new ConsoleAdministrationSrvImpl(administration, LOGGER);
    try (var scanner = new Scanner(System.in)) {
      var exit = false;
      while (!exit) {
        printMainMenu();
        var cmd = readString(scanner);
        if ("1".equals(cmd)) {
          consoleAdministration.getAllSubmittedTickets();
        } else if ("2".equals(cmd)) {
          consoleAdministration.performLottery();
        } else if ("3".equals(cmd)) {
          consoleAdministration.resetLottery();
        } else if ("4".equals(cmd)) {
          exit = true;
        } else {
          LOGGER.info("Unknown command: {}", cmd);
        }
      }
    }
  }

  private static void printMainMenu() {
    LOGGER.info("");
    LOGGER.info("### Lottery Administration Console ###");
    LOGGER.info("(1) Show all submitted tickets");
    LOGGER.info("(2) Perform lottery draw");
    LOGGER.info("(3) Reset lottery ticket database");
    LOGGER.info("(4) Exit");
  }

  private static String readString(Scanner scanner) {
    LOGGER.info("> ");
    return scanner.next();
  }
}
