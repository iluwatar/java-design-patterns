
import com.iluwatar.nima.Account;
import com.iluwatar.nima.Bank;
import org.junit.Test;

import static org.junit.Assert.*;


public class BankTest {
    Bank bank = new Bank();
    Account account = new Account(1000);


    // create an account table, then account table is not empty
    @Test
    public void createAccountTest(){
        bank.createAccount(123456,1000);
        assertFalse(bank.accountTable.isEmpty());
    }

    // delete an existing table, then account table is  empty
    @Test
    public void deleteAccountTest(){
        bank.createAccount(123456,1000);
        bank.deleteAccount(123456);
        assertTrue(bank.accountTable.isEmpty());
    }

    // get an account number, if the account number is not existing, then return null
    @Test
    public void getAccountTest(){
        bank.createAccount(123456,1000);
        assertNull(bank.getAccount(7890));

    }






}
