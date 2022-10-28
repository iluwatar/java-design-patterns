package com.iluwatar.nima;

import java.util.Hashtable;

public class Bank {
    // create accountTable, key: accountNumber, value: amount
    public Hashtable<Integer, Account> accountTable;

    public Bank() {
        accountTable =new Hashtable<Integer,Account>();
    }

//    public Bank() {
//
//    }

//    //public HashMap<Integer, Account> getAccountTable() {
//        return accountTable;
//    }

//   // public void setAccountTable(HashMap<Integer, Account> accountTable) {
//        this.accountTable = accountTable;
//    }

    // create a new bank account
    public void createAccount(Integer accountNumber, Integer amount) {
        accountTable.put(accountNumber, new Account(amount));
    }

    // delete an existing account
    public void deleteAccount(Integer accountment){
        accountTable.clear();
    }

    // get bank account information
    public Account getAccount(Integer accountNumber){
        return accountTable.get(accountNumber);
    }

    public static void main(String[] args) {
        Bank bank = new Bank();
        bank.createAccount(123456,1000);
        // bank.deleteAccount(123456);
        // System.out.println(bank.accountTable.isEmpty());
        //System.out.println(bank.accountTable.isEmpty());
        // System.out.println(bank.getAccount(123456));

    }
}
