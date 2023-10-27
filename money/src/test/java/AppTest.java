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
        assertEquals("Primary Balance: 10000 USD", lines[0].trim().substring(lines[0].length()-26));
        assertEquals("Secondary Balance: 5000 EUR", lines[1].trim().substring(lines[1].length()-27));
        assertEquals("Allocated Balances:", lines[2].trim().substring(lines[2].length()-19));
        assertEquals("Account 1: 13000 USD", lines[3].trim().substring(lines[3].length()-20));
        assertEquals("Account 2: 3000 USD", lines[4].trim().substring(lines[4].length()-19));
    }
}
