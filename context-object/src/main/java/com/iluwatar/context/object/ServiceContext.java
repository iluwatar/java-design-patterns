package com.iluwatar.context.object;

/**
 * Where context objects are defined.
 */
public class ServiceContext {

    String ACCOUNT_SERVICE, SESSION_SERVICE, SEARCH_SERVICE;

    public void setACCOUNT_SERVICE(String ACCOUNT_SERVICE) {
        this.ACCOUNT_SERVICE = ACCOUNT_SERVICE;
    }

    public void setSESSION_SERVICE(String SESSION_SERVICE) {
        this.SESSION_SERVICE = SESSION_SERVICE;
    }

    public void setSEARCH_SERVICE(String SEARCH_SERVICE) {
        this.SEARCH_SERVICE = SEARCH_SERVICE;
    }

    public String getACCOUNT_SERVICE() {
        return ACCOUNT_SERVICE;
    }

    public String getSESSION_SERVICE() {
        return SESSION_SERVICE;
    }

    public String getSEARCH_SERVICE() {
        return SEARCH_SERVICE;
    }

    public String toString() { return ACCOUNT_SERVICE + " " + SESSION_SERVICE + " " + SEARCH_SERVICE;}
}
