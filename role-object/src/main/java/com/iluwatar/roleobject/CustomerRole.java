package com.iluwatar.roleobject;

import java.util.Optional;

public class CustomerRole extends Customer{
    @Override
    public boolean addRole(Role role) {
        return false;
    }

    @Override
    public boolean hasRole(Role role) {
        return false;
    }

    @Override
    public boolean remRole(Role role) {
        return false;
    }

    @Override
    public <T extends Customer> Optional<T> getRole(Role role, Class<T> expectedRole) {
        return Optional.empty();
    }
}
