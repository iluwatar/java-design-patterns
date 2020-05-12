/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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

package com.iluwatar.microkernel.externals;

import com.iluwatar.microkernel.microkernel.BudgetMicrokernel;
import com.iluwatar.microkernel.models.Account;
import com.iluwatar.microkernel.utils.AccountUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents an external server.
 * This class counts income and outgoing forecasts
 * for the next period.
 */
public class ForecastServer implements ResultProcessorServer {

  private BudgetMicrokernel microkernel;
  private Map<Integer, Double> incomes;
  private Map<Integer, Double> outgoings;

  /**
   * Use this constructor to create an ForecastServer with all details.
   * @param microkernel as the core which does the basic calculations
   */
  public ForecastServer(BudgetMicrokernel microkernel) {
    this.microkernel = microkernel;
    this.incomes = new HashMap<>();
    this.outgoings = new HashMap<>();
  }

  @Override
  public void receiveRequest() {
    dispatchRequest();
    executeService();
  }

  private void dispatchRequest() {
    List<Account> recentYearsAccounts = this.microkernel.getRecentYearsAccounts();
    recentYearsAccounts.forEach(account -> {
      if (AccountUtil.INCOME == account.getAccountNumber()) {
        this.incomes.put(account.getYear(), account.getCurrentValue());
      } else if (AccountUtil.OUTGOING == account.getAccountNumber()) {
        this.outgoings.put(account.getYear(), account.getCurrentValue());
      }
    });
  }

  private void executeService() {
    double income = calculateForecast(this.incomes);
    System.out.println("The next income forecast: " + income);
    double outgoing = calculateForecast(this.outgoings);
    System.out.println("The next outgoing forecast: " + outgoing);

  }

  /**
   * Averages the given annual results.
   * @param results  as basis for calculation
   * @return average of the results
   */
  private double calculateForecast(Map<Integer, Double> results) {
    double result = 0;
    if (results.size() > 0) {
      for (double amount : results.values()) {
        result += amount;
      }
      return result / results.size();
    } else {
      return result;
    }
  }
}
