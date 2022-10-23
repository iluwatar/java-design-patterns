package com.iluwatar.dci_pattern.simple;

import com.iluwatar.dci_pattern.context.Context;
import com.iluwatar.dci_pattern.data.Account;
import com.iluwatar.dci_pattern.data.Bank;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class dciTest {
    private Bank bank = new Bank();
    private Context context = new Context();

    @Test
    public void testTransfer() {
        Account a = new Account(1000);
        Account b = new Account(1000);
        bank.accountTable.put(123456,a);
        bank.accountTable.put(789011, b);
        context.transfer(100,123456,789011,bank);
        assertEquals(900,bank.getAccount(123456).balance);
    }

}
