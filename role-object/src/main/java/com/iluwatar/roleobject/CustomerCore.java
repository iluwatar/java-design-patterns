package com.iluwatar.roleobject;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class CustomerCore extends Customer {

    private Map<Role, CustomerRole> roles;


    @Override
    public boolean addRole(Role role) {
        return role.instance()
                .map(rI -> roles.put(role, rI))
                .isPresent();
    }

    @Override
    public boolean hasRole(Role role) {
        return roles.containsKey(role);
    }

    @Override
    public boolean remRole(Role role) {
        return Objects.nonNull(roles.remove(role));
    }

    @Override
    public <T extends Customer> Optional<T> getRole(Role role, Class<T> expectedRole) {
        return Optional.ofNullable(roles.get(role))
                .filter(expectedRole::isInstance)
                .map(expectedRole::cast);
    }
}
