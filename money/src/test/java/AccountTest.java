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
import com.iluwatar.Account;
import com.iluwatar.Currency;
import com.iluwatar.Money;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;

public class AccountTest {
    private Account account;
    private Currency usd;
    private Currency eur;

    @BeforeMethod
    public void setUp() {
        usd = Currency.usd();
        eur = Currency.eur();
        account = new Account(usd, eur);
    }

    @Test
    public void testDepositAndWithdraw() {
        Money depositUSD = new Money(1000, usd);
        Money depositEUR = new Money(500, eur);

        // Deposit money
        account.deposit(depositUSD);
        account.deposit(depositEUR);

        // Check balances
        assertEquals(account.getPrimaryBalance().getAmount(), 1000);
        assertEquals(account.getSecondaryBalance().getAmount(), 500);

        Money withdrawUSD = new Money(300, usd);
        Money withdrawEUR = new Money(200, eur);

        // Withdraw money
        account.withdraw(withdrawUSD);
        account.withdraw(withdrawEUR);

        // Check balances after withdrawal
        assertEquals(account.getPrimaryBalance().getAmount(), 700);
        assertEquals(account.getSecondaryBalance().getAmount(), 300);
    }

    @Test
    public void testInvalidCurrencyDeposit() {
        Money depositInvalid = new Money(1000, new Currency(100, "InvalidCurrency"));
        IllegalArgumentException exception = null;
        try {
            account.deposit(depositInvalid);
        } catch (IllegalArgumentException e) {
            exception = e;
        }

        assertTrue(exception.getMessage().contains("Invalid currency for this account"));
    }

    @Test
    public void testInsufficientBalanceWithdraw() {
        Money depositUSD = new Money(500, usd);
        account.deposit(depositUSD);

        Money withdrawUSD = new Money(600, usd);
        IllegalArgumentException exception = null;
        try {
            account.withdraw(withdrawUSD);
        } catch (IllegalArgumentException e) {
            exception = e;
        }

        assertTrue(exception.getMessage().contains("Insufficient balance in primary currency"));
    }
}
