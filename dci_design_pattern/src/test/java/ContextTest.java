
import com.iluwatar.nima.Account;
import com.iluwatar.nima.Bank;
import com.iluwatar.nima.Context;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ContextTest {
    private Bank bank = new Bank();
    private Context context = new Context();

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
        context.transfer(300,123456,789011,bank);

        int balance = bank.getAccount(123456).balance;
        //int balance2 = bank.getAccount(789011).balance;
        //System.out.println(balance2);
        assertEquals(700,balance);
    }

    @Test
    public void testTransfer3(){
        Account a = new Account(1000);
        Account b = new Account(1000);
        bank.accountTable.put(123456,a);
        bank.accountTable.put(789011, b);
        context.receive(100,123456,789011,bank);
        int balance = bank.getAccount(789011).balance;
        assertEquals(1100,balance);

    }


}