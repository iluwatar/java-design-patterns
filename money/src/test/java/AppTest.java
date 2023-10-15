import com.iluwatar.Account;
import com.iluwatar.App;
import com.iluwatar.Currency;
import com.iluwatar.Money;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class AppTest {

    @Test
    public void testAppOutput() {
        // Redirect the standard output to capture the printed messages
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Run the main method of the App class
        App.main(new String[]{});

        // Restore the standard output
        System.setOut(System.out);

        // Split the captured output into lines
        String[] lines = outContent.toString().split(System.lineSeparator());

        // Check the expected output
        assertEquals("Primary Balance: 10000 USD", lines[0].trim());
        assertEquals("Secondary Balance: 5000 EUR", lines[1].trim());
        assertEquals("Allocated Balances:", lines[2].trim());
        assertEquals("Account 1: 13000 USD", lines[3].trim());
        assertEquals("Account 2: 3000 USD", lines[4].trim());
    }
}
