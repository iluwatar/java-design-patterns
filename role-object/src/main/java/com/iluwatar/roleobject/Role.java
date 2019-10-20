package com.iluwatar.roleobject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.rmi.runtime.Log;

import java.util.Optional;

public enum Role {
    Borrower(BorrowerRole.class), Investor(InvestorRole.class);

    private Class<? extends Customer> typeCst;

    Role(Class<? extends Customer> typeCst) {
        this.typeCst = typeCst;
    }
    private static final Logger logger = LoggerFactory.getLogger(Role.class);

    @SuppressWarnings("unchecked")
    <T extends CustomerRole>Optional<T> instance(){
        Class<? extends Customer> typeCst = this.typeCst;
        try {
            return (Optional<T>) Optional.of(typeCst.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error("error creating an object",e);
        }
        return Optional.empty();
    }

}
