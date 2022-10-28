
import com.iluwatar.nima.Account;
import com.iluwatar.nima.Bank;
import com.iluwatar.nima.Context;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ContextTest {
    private final Bank bank = new Bank();
    private final Context context = new Context();

    @Test
    public void testTransfer() {
        // test context transfer function
        Account a = new Account(1000);
        Account b = new Account(1000);
        bank.accountTable.put(123456,a);
        bank.accountTable.put(789011, b);
        context.transfer(100,123456,789011,bank);
        int balance = bank.getAccount(123456).balance;
        assertEquals(900,balance);

    }

    //if transfer is successful, then b account will increase to 1100;

    @Test
    public void testTransfer2() {
        Account a = new Account(1000);
        Account b = new Account(1000);
        bank.accountTable.put(123456,a);
        bank.accountTable.put(789011, b);
        context.transfer(200,123456,789011,bank);
        int balance = bank.getAccount(123456).balance;
        assertEquals(800,balance);
    }


}
