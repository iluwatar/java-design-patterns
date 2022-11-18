package com.iluwatar.context.object;

import lombok.Getter;

@Getter
public class LayerA {

    private ServiceContext context;

    public LayerA() {
        context = ServiceContextFactory.createContext();
    }

    public void addAccountInfo(String accountService) {
        context.setAccountService(accountService);
    }
}
