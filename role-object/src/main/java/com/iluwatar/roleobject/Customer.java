package com.iluwatar.roleobject;

import java.util.Optional;

public abstract class Customer {

    public abstract boolean addRole(Role role);
    public abstract boolean hasRole(Role role);
    public abstract boolean remRole(Role role);
    public abstract <T extends Customer> Optional<T> getRole(Role role,Class<T> expectedRole);

}
