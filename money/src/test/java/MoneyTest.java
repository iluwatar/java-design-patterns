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
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MoneyTest {
    private Currency usd;
    private Currency eur;
    private Money money1;
    private Money money2;
    private Account account1;
    private Account account2;

    @Before
    public void setUp() {
        usd = Currency.usd();
        eur = Currency.eur();
        money1 = new Money(1000, usd);
        money2 = new Money(500, usd);
        account1 = new Account(usd, eur);
        account2 = new Account(usd, eur);
    }

    @Test
    public void testGetAmount() {
        assertEquals(1000, money1.getAmount());
        assertEquals(500, money2.getAmount());
    }

    @Test
    public void testGetCurrency() {
        assertEquals(usd, money1.getCurrency());
        assertEquals(usd, money2.getCurrency());
    }

    @Test
    public void testAdd() {
        Money result = money1.add(money2);
        assertEquals(1500, result.getAmount());
        assertEquals(usd, result.getCurrency());
    }

    @Test
    public void testSubtract() {
        Money result = money1.subtract(money2);
        assertEquals(500, result.getAmount());
        assertEquals(usd, result.getCurrency());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSubtractWithDifferentCurrencies() {
        Money moneyInEUR = new Money(500, eur);
        money1.subtract(moneyInEUR);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSubtractNegativeAmount() {
        Money negativeMoney = new Money(1500, usd);
        money1.subtract(negativeMoney);
    }

    @Test
    public void testMultiplyBy() {
        Money result = money1.multiplyBy(1.5);
        assertEquals(1500, result.getAmount());
        assertEquals(usd, result.getCurrency());
    }

    @Test
    public void testAllocate() {
        Account[] accounts = {account1, account2};
        money1.allocate(accounts, 50, 50);

        assertEquals(500, account1.getPrimaryBalance().getAmount());
        assertEquals(500, account2.getPrimaryBalance().getAmount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAllocateInvalidPercentages() {
        Account[] accounts = {account1, account2};
        money1.allocate(accounts, 40, 60);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAllocateDifferentCurrencies() {
        Account account3 = new Account(usd, Currency.usd());
        Account[] accounts = {account1, account3};
        money1.allocate(accounts, 50, 50);
    }

    @Test
    public void testDeposit() {
        Money depositMoney = new Money(200, usd);
        account1.deposit(depositMoney);
        assertEquals(200, account1.getPrimaryBalance().getAmount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDepositInvalidCurrency() {
        Money depositMoney = new Money(200, eur);
        account1.deposit(depositMoney);
    }

    @Test
    public void testWithdraw() {
        Money depositMoney = new Money(200, usd);
        account1.deposit(depositMoney);
        Money withdrawalMoney = new Money(100, usd);
        account1.withdraw(withdrawalMoney);
        assertEquals(100, account1.getPrimaryBalance().getAmount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithdrawInvalidCurrency() {
        Money withdrawalMoney = new Money(200, eur);
        account1.withdraw(withdrawalMoney);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithdrawInsufficientBalance() {
        Money withdrawalMoney = new Money(200, usd);
        account1.withdraw(withdrawalMoney);
    }
}
