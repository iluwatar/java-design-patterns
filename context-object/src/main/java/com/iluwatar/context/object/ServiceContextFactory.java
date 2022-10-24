package com.iluwatar.context.object;

/**
 * An interface to create context objects passed through layers.
 */
public class ServiceContextFactory {

    public static ServiceContext createContext() {
        return new ServiceContext();
    }

    public static ServiceContext createAccountContext(String accountService) {
        ServiceContext accountContext = new ServiceContext();
        accountContext.setACCOUNT_SERVICE(accountService);
        return accountContext;
    }

    public static ServiceContext createSessionContext(String sessionService) {
        ServiceContext sessionContext = new ServiceContext();
        sessionContext.setACCOUNT_SERVICE(sessionService);
        return sessionContext;
    }

    public static ServiceContext createSearchContext(String searchService) {
        ServiceContext searchContext = new ServiceContext();
        searchContext.setACCOUNT_SERVICE(searchService);
        return searchContext;
    }
}
