package com.iluwatar.roleobject;

import java.util.Optional;

/**
 * The main abstraction to work with Customer.
 */
public abstract class Customer {

    /**
     * Add specific role @see {@link Role}
     *
     * @param role to add
     * @return true if the operation has been successful otherwise false
     */
    public abstract boolean addRole(Role role);

    /**
     * Check specific role @see {@link Role}
     *
     * @param role to check
     * @return true if the role exists otherwise false
     */

    public abstract boolean hasRole(Role role);

    /**
     * Remove specific role @see {@link Role}
     *
     * @param role to remove
     * @return true if the operation has been successful otherwise false
     */
    public abstract boolean remRole(Role role);

    /**
     * Get specific instance associated with this role @see {@link Role}
     *
     * @param role         to get
     * @param expectedRole instance class expected to get
     * @return optional with value if the instance exists and corresponds expected class
     */
    public abstract <T extends Customer> Optional<T> getRole(Role role, Class<T> expectedRole);


    public static Customer newCustomer() {
        return new CustomerCore();
    }

    public static Customer newCustomer(Role... role) {
        Customer customer = newCustomer();
        for (Role r : role) {
            customer.addRole(r);
        }
        return customer;
    }

}
