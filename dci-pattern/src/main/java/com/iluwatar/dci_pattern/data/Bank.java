package com.iluwatar.dci_pattern.data;

import java.util.HashMap;

public class Bank {

    // create accountTable, key: accountNumber, value: amount
    public HashMap<Integer, Account> accountTable;

    public Bank(HashMap<Integer, Account> accountTable) {
        this.accountTable = accountTable;
    }

    public Bank() {

    }

    public HashMap<Integer, Account> getAccountTable() {
        return accountTable;
    }

    public void setAccountTable(HashMap<Integer, Account> accountTable) {
        this.accountTable = accountTable;
    }

    // create a new bank account
    public void createAccount(Integer accountNumber, Integer amount) {
        accountTable.put(accountNumber, new Account(amount));
    }

    // get bank account information
    public Account getAccount(Integer accountNumber){
        return accountTable.get(accountNumber);
    }

}
