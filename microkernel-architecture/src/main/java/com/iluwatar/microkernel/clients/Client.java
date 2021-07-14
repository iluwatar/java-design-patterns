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

package com.iluwatar.microkernel.clients;

import com.iluwatar.microkernel.adapters.Adapter;
import com.iluwatar.microkernel.microkernel.BudgetMicrokernel;
import com.iluwatar.microkernel.models.Account;
import com.iluwatar.microkernel.utils.AccountUtil;
import com.iluwatar.microkernel.utils.RequestUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a client,
 * which represents an application.
 */
public class Client {
  private Adapter adapter;

  /**
   * Use this constructor to create a Client.
   * This method creates adapter with all dependencies.
   */
  public Client() {
    adapter = new Adapter(new BudgetMicrokernel(initBudgetAccounts()));
  }

  private Map<Integer, Account> initBudgetAccounts() {
    Map<Integer, Account> accounts = new HashMap<>();
    accounts.put(0, new Account(new ArrayList<>(), AccountUtil.INCOME, AccountUtil.INCOME_NAME,
            2017, true, false, 100000));
    accounts.put(1, new Account(new ArrayList<>(), AccountUtil.OUTGOING, AccountUtil.OUTGOING_NAME,
            2017, true, true, 80000));
    accounts.put(2, new Account(new ArrayList<>(), AccountUtil.INCOME, AccountUtil.INCOME_NAME,
            2018, true, false, 150000));
    accounts.put(3, new Account(new ArrayList<>(), AccountUtil.OUTGOING, AccountUtil.OUTGOING_NAME,
            2018, true, true, 135000));
    accounts.put(4, new Account(new ArrayList<>(), AccountUtil.INCOME, AccountUtil.INCOME_NAME,
            2019, true, false, 180000));
    accounts.put(5, new Account(new ArrayList<>(), AccountUtil.OUTGOING, AccountUtil.OUTGOING_NAME,
            2019, true, true, 140000));
    accounts.put(6, new Account(new ArrayList<>(), AccountUtil.INCOME, AccountUtil.INCOME_NAME,
            2020, false, false, 150000));
    accounts.put(7, new Account(new ArrayList<>(), AccountUtil.OUTGOING, AccountUtil.OUTGOING_NAME,
            2020, false, true, 115000));
    return accounts;
  }

  public void printForecast() {
    this.adapter.callService(RequestUtil.FORECAST);
  }

  public void printReport() {
    this.adapter.callService(RequestUtil.RESULT);
  }

}
