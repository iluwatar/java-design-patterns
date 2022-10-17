package com.iluwatar.daofactory;

/**
 * An interface that determine the condition that
 * Account DAO must support
 */
public interface AccountDAO {
    public boolean insertAccount(String firstName, String lastName, String location);
    public Account findAccount(String firstName, String lastName, String location);
    public boolean deleteAccount(String firstName, String lastName, String location);
    public boolean updateAccount(Account acc);
}
