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
import com.iluwatar.microkernel.models.FlowingType;
import com.iluwatar.microkernel.models.Transaction;
import com.iluwatar.microkernel.utils.AccountUtil;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents an external server.
 * This class counts the profit and the tax for
 * the actual period, and account that by the microkernel.
 *
 */
public class ResultReportServer implements ResultProcessorServer {

  private BudgetMicrokernel microkernel;
  private Map<Integer, Double> untaxedProfit;
  private static final double PROFIT_TAX = 15;

  /**
   * Use this constructor to create an ResultReportServer with all details.
   * @param microkernel as the core which does the basic calculations
   */
  public ResultReportServer(BudgetMicrokernel microkernel) {
    this.microkernel = microkernel;
    this.untaxedProfit = new HashMap<>();
  }

  @Override
  public void receiveRequest() {
    dispatchRequest();
    executeService();
  }

  private void dispatchRequest() {
    List<Account> currentAccounts = this.microkernel.getCurrentAccounts();
    currentAccounts.forEach(account -> {
      if (untaxedProfit.get(account.getYear()) == null) {
        this.untaxedProfit.put(account.getYear(), 0d);
      }
      Double amount = this.untaxedProfit.get(account.getYear());
      if (AccountUtil.INCOME == account.getAccountNumber()) {
        this.untaxedProfit.put(account.getYear(), amount + account.getCurrentValue());
      } else if (AccountUtil.OUTGOING == account.getAccountNumber()) {
        this.untaxedProfit.put(account.getYear(), amount - account.getCurrentValue());
      }
    });
  }

  private void executeService() {
    double untaxedProfit = (double) this.untaxedProfit.values().toArray()[0];
    int year = (int) this.untaxedProfit.keySet().toArray()[0];
    double tax = untaxedProfit * this.PROFIT_TAX / 100;
    System.out.println("In " + year
            + " the untaxed profit is " + untaxedProfit);
    System.out.println("The tax is " + this.PROFIT_TAX + "%.");
    System.out.println("Debt to the state in the form of tax: " + tax + ".");
    Transaction taxTransaction = new Transaction("Determining a tax liability for "
            + year + "'s profit.", LocalDate.now(), tax, FlowingType.CREDIT);
    this.microkernel.executeMechanism(taxTransaction);
  }
}
