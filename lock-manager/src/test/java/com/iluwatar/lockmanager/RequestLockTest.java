package src.test.java.com.iluwatar.lockmanager;

import org.testng.annotations.Test;
import src.main.java.com.iluwatar.lockmanager.InvalidNameException;
import src.main.java.com.iluwatar.lockmanager.Manager;

import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

public class RequestLockTest {
    @Test
    void RequestLockTest() throws InvalidNameException {
        Manager manager = Manager.getManager("TESTING");
        Object lockable1 = new Object();
        Object lockable2 = new Object();
        assertTrue(manager.canLock("lock1",lockable1));
        assertTrue(manager.canLock("lock1",lockable1));
        assertTrue(manager.canLock("lock2",lockable2));
        assertTrue(manager.canLock("lock2",lockable2));
        assertFalse(manager.canLock("lock1",lockable2));
        assertFalse(manager.canLock("lock2",lockable1));
    }
}
