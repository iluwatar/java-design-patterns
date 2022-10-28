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

package com.iluwatar.roleobject;

import static com.iluwatar.roleobject.Role.Borrower;
import static com.iluwatar.roleobject.Role.Investor;

import lombok.extern.slf4j.Slf4j;

/**
 * The Role Object pattern suggests to model context-specific views of an object as separate role
 * objects which are dynamically attached to and removed from the core object. We call the resulting
 * composite object structure, consisting of the core and its role objects, a subject. A subject
 * often plays several roles and the same role is likely to be played by different subjects. As an
 * example consider two different customers playing the role of borrower and investor, respectively.
 * Both roles could as well be played by a single {@link Customer} object. The common superclass for
 * customer-specific roles is provided by {@link CustomerRole}, which also supports the {@link
 * Customer} interface.
 *
 * <p>The {@link CustomerRole} class is abstract and not meant to be instantiated.
 * Concrete subclasses of {@link CustomerRole}, for example {@link BorrowerRole} or {@link
 * InvestorRole}, define and implement the interface for specific roles. It is only these subclasses
 * which are instantiated at runtime. The {@link BorrowerRole}  class defines the context-specific
 * view of {@link Customer} objects as needed by the loan department. It defines additional
 * operations to manage the customer’s credits and securities. Similarly, the {@link InvestorRole}
 * class adds operations specific to the investment department’s view of customers. A client like
 * the loan application may either work with objects of the {@link CustomerRole} class, using the
 * interface class {@link Customer}, or with objects of concrete {@link CustomerRole} subclasses.
 * Suppose the loan application knows a particular {@link Customer} instance through its {@link
 * Customer} interface. The loan application may want to check whether the {@link Customer} object
 * plays the role of Borrower. To this end it calls {@link Customer#hasRole(Role)} with a suitable
 * role specification. For the purpose of our example, let’s assume we can name roles with enum. If
 * the {@link Customer} object can play the role named “Borrower,” the loan application will ask it
 * to return a reference to the corresponding object. The loan application may now use this
 * reference to call Borrower-specific operations.
 */
@Slf4j
public class ApplicationRoleObject {

  /**
   * Main entry point.
   *
   * @param args program arguments
   */
  public static void main(String[] args) {
    var customer = Customer.newCustomer(Borrower, Investor);

    LOGGER.info(" the new customer created : {}", customer);

    var hasBorrowerRole = customer.hasRole(Borrower);
    LOGGER.info(" customer has a borrowed role - {}", hasBorrowerRole);
    var hasInvestorRole = customer.hasRole(Investor);
    LOGGER.info(" customer has an investor role - {}", hasInvestorRole);

    customer.getRole(Investor, InvestorRole.class)
        .ifPresent(inv -> {
          inv.setAmountToInvest(1000);
          inv.setName("Billy");
        });
    customer.getRole(Borrower, BorrowerRole.class)
        .ifPresent(inv -> inv.setName("Johny"));

    customer.getRole(Investor, InvestorRole.class)
        .map(InvestorRole::invest)
        .ifPresent(LOGGER::info);

    customer.getRole(Borrower, BorrowerRole.class)
        .map(BorrowerRole::borrow)
        .ifPresent(LOGGER::info);
  }
}
