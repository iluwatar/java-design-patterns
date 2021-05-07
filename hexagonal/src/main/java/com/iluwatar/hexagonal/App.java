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

package com.iluwatar.hexagonal;

import com.google.inject.Guice;
import com.iluwatar.hexagonal.domain.LotteryAdministration;
import com.iluwatar.hexagonal.domain.LotteryService;
import com.iluwatar.hexagonal.module.LotteryTestingModule;
import com.iluwatar.hexagonal.sampledata.SampleData;

/**
 * Hexagonal Architecture pattern decouples the application core from the services it uses. This
 * allows the services to be plugged in and the application will run with or without the services.
 *
 * <p>The core logic, or business logic, of an application consists of the algorithms that are
 * essential to its purpose. They implement the use cases that are the heart of the application.
 * When you change them, you change the essence of the application.
 *
 * <p>The services are not essential. They can be replaced without changing the purpose of the
 * application. Examples: database access and other types of storage, user interface components,
 * e-mail and other communication components, hardware devices.
 *
 * <p>This example demonstrates Hexagonal Architecture with a lottery system. The application core
 * is separate from the services that drive it and from the services it uses.
 *
 * <p>The primary ports for the application are console interfaces {@link
 * com.iluwatar.hexagonal.administration.ConsoleAdministration} through which the lottery round is
 * initiated and run and {@link com.iluwatar.hexagonal.service.ConsoleLottery} that allows players
 * to submit lottery tickets for the draw.
 *
 * <p>The secondary ports that application core uses are{@link
 * com.iluwatar.hexagonal.banking.WireTransfers} which is a banking service, {@link
 * com.iluwatar.hexagonal.eventlog.LotteryEventLog} that delivers eventlog as lottery events occur
 * and {@link com.iluwatar.hexagonal.database.LotteryTicketRepository} that is the storage for the
 * lottery tickets.
 */
public class App {

  /**
   * Program entry point.
   */
  public static void main(String[] args) {

    var injector = Guice.createInjector(new LotteryTestingModule());

    // start new lottery round
    var administration = injector.getInstance(LotteryAdministration.class);
    administration.resetLottery();

    // submit some lottery tickets
    var service = injector.getInstance(LotteryService.class);
    SampleData.submitTickets(service, 20);

    // perform lottery
    administration.performLottery();
  }
}
