package com.iluwatar.singleton;

/**
 * Date: 06/18/23 - 16:29 PM.
 *
 * @author Owen Leung
 */
public class BillPughImplementationTest
        extends SingletonTest<BillPughImplementation>{
    /**
     * Create a new singleton test instance using the given 'getInstance' method.
     */
    public BillPughImplementationTest() {
        super(BillPughImplementation::getInstance);
    }
}
