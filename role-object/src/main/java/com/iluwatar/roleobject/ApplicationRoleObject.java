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
package com.iluwatar.roleobject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.iluwatar.roleobject.Role.*;

/**
 * The Role Object pattern suggests to model context-specific views
 * of an object as separate role objects which are
 * dynamically attached to and removed from the core object.
 * We call the resulting composite object structure,
 * consisting of the core and its role objects, a subject.
 * A subject often plays several roles and the same role is likely to
 * be played by different subjects.
 * As an example consider two different customers playing the role of borrower and
 * investor, respectively. Both roles could as well be played by a single Customer object.
 * The common superclass for customer-specific roles is provided by CustomerRole,
 * which also supports the Customer interface.
 * <p>
 * The CustomerRole class is abstract and not meant to be instantiated.
 * Concrete subclasses of CustomerRole, for example Borrower or Investor,
 * define and implement the interface for specific roles. It is only
 * these subclasses which are instantiated at runtime.
 * The Borrower class defines the context-specific view of Customer objects as needed by the loan department.
 * It defines additional operations to manage the customer’s
 * credits and securities. Similarly, the Investor class adds operations specific
 * to the investment department’s view of customers.
 * A client like the loan application may either work with objects of the CustomerCore class, using the interface class
 * Customer, or with objects of concrete CustomerRole subclasses. Suppose the loan application knows a particular
 * Customer instance through its Customer interface. The loan application may want to check whether the Customer
 * object plays the role of Borrower.
 * To this end it calls hasRole() with a suitable role specification. For the purpose of
 * our example, let’s assume we can name roles with enum.
 * If the Customer object can play the role named “Borrower,” the loan application will ask it to return a reference to the corresponding object.
 * The loan application may now use this reference to call Borrower-specific operations.
 */
public class ApplicationRoleObject {

    private static final Logger logger = LoggerFactory.getLogger(Role.class);

    public static void main(String[] args) {
        Customer customer = Customer.newCustomer(Borrower, Investor);

        logger.info(" the new customer created : {}", customer);

        boolean hasBorrowerRole = customer.hasRole(Borrower);
        logger.info(" customer has a borrowed role - {}", hasBorrowerRole);
        boolean hasInvestorRole = customer.hasRole(Investor);
        logger.info(" customer has an investor role - {}", hasInvestorRole);

        customer.getRole(Investor, InvestorRole.class)
                .ifPresent(inv -> {
                    inv.setAmountToInvest(1000);
                    inv.setName("Billy");
                });
        customer.getRole(Borrower, BorrowerRole.class)
                .ifPresent(inv -> inv.setName("Johny"));

        customer.getRole(Investor, InvestorRole.class)
                .map(InvestorRole::invest)
                .ifPresent(logger::info);

        customer.getRole(Borrower, BorrowerRole.class)
                .map(BorrowerRole::borrow)
                .ifPresent(logger::info);
    }


}
