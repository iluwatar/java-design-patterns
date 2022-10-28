
import com.iluwatar.nima.Account;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountTest {
    Account account = new Account(1000);

    @Test
    public void balanceTest(){

        assertEquals("1000", account.balance().toString());
    }

    /*
    increase account by 1000
     */
    @Test
    public void increaseBalanceTest(){
        account.increase(1000);
        assertEquals("2000", account.balance().toString());
    }

    /*
increase account by 1000
 */
    @Test
    public void decreaseBalanceTest(){
        account.decrease(500);
        assertEquals("500", account.balance().toString());
    }




}
