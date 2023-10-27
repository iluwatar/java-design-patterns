import com.iluwatar.App;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.testng.Assert.assertEquals;

public class AppTest {

    private PrintStream originalOut;
    private ByteArrayOutputStream outContent;

    @BeforeMethod
    public void setUp() {
        outContent = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outContent));
    }

    @AfterMethod
    public void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    public void testAppOutput() {
        App.main(new String[]{});
        String[] lines = outContent.toString().split(System.lineSeparator());
        assertEquals(lines[0].substring(lines[0].length() - 26).trim(), "Primary Balance: 10000 USD");
        assertEquals(lines[1].substring(lines[1].length() - 27).trim(), "Secondary Balance: 5000 EUR");
        assertEquals(lines[2].substring(lines[2].length() - 19).trim(), "Allocated Balances:");
        assertEquals(lines[3].substring(lines[3].length() - 20).trim(), "Account 1: 13000 USD");
        assertEquals(lines[4].substring(lines[4].length() - 19).trim(), "Account 2: 3000 USD");

    }
}
