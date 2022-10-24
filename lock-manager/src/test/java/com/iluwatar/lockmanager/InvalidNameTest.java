package src.test.java.com.iluwatar.lockmanager;

import org.testng.annotations.Test;
import src.main.java.com.iluwatar.lockmanager.InvalidNameException;
import src.main.java.com.iluwatar.lockmanager.Manager;

public class InvalidNameTest {

    @Test(expectedExceptions = InvalidNameException.class)
    void InvalidNameTestGetManagerEmpty() throws InvalidNameException {
        Manager manager = Manager.getManager("TESTING");
        manager.getManager("");
    }

    @Test(expectedExceptions = InvalidNameException.class)
    void InvalidNameTestGetManagerNull() throws InvalidNameException {
        Manager manager = Manager.getManager("TESTING");
        manager.getManager(null);
    }

    @Test(expectedExceptions = InvalidNameException.class)
    void InvalidNameTestCanLockEmpty() throws InvalidNameException {
        Object object = new Object();
        Manager manager = Manager.getManager("TESTING");
        manager.canLock("",object);
    }

    @Test(expectedExceptions = InvalidNameException.class)
    void InvalidNameTestCanLockNull() throws InvalidNameException {
        Object object = new Object();
        Manager manager = Manager.getManager("TESTING");
        manager.canLock(null,object);
    }
}
