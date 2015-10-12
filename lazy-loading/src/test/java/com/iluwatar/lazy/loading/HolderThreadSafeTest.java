package com.iluwatar.lazy.loading;

import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Using reflection this test shows that the heavy field is not instantiated until the method getHeavy is called
 *
 * Created by jones on 11/10/2015.
 */
public class HolderThreadSafeTest {

    @Test
    public void test() throws IllegalAccessException {
        HolderThreadSafe hts = new HolderThreadSafe();

        {//first call is null
            Field[] f = HolderThreadSafe.class.getDeclaredFields();
            assertEquals("One field only in HolderThreadSafe", 1, f.length);
            f[0].setAccessible(true);

            assertNull(f[0].get(hts));
        }

        // now it is lazily loaded
        hts.getHeavy();

        {//now it is not null - call via reflection so that the test is the same before and after
            Field[] f = HolderThreadSafe.class.getDeclaredFields();
            assertEquals("One field only in HolderThreadSafe", 1, f.length);
            f[0].setAccessible(true);

            assertNotNull(f[0].get(hts));
        }
    }
}
