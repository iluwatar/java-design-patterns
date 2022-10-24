package src.test.java.com.iluwatar.lockmanager;

import org.testng.annotations.Test;
import src.main.java.com.iluwatar.lockmanager.InvalidNameException;
import src.main.java.com.iluwatar.lockmanager.Manager;

import static org.testng.AssertJUnit.assertEquals;

public class RemoveLockTest {
    @Test
    void RemoveLockTest() throws InvalidNameException {
        Manager manager = Manager.getManager("TESTING");
        Object lockable = new Object();
        manager.canLock("Lock",lockable);
        assertEquals("Lock",manager.removeLock(lockable));
    }
}
