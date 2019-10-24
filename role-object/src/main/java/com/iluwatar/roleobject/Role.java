package com.iluwatar.roleobject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Possible roles
 */
public enum Role {
    Borrower(BorrowerRole.class), Investor(InvestorRole.class);

    private Class<? extends CustomerRole> typeCst;

    Role(Class<? extends CustomerRole> typeCst) {
        this.typeCst = typeCst;
    }

    private static final Logger logger = LoggerFactory.getLogger(Role.class);

    @SuppressWarnings("unchecked")
    public <T extends CustomerRole> Optional<T> instance() {
        Class<? extends CustomerRole> typeCst = this.typeCst;
        try {
            return (Optional<T>) Optional.of(typeCst.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error("error creating an object", e);
        }
        return Optional.empty();
    }

}
