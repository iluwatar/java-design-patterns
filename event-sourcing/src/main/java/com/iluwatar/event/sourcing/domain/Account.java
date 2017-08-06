package com.iluwatar.event.sourcing.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by serdarh on 06.08.2017.
 */
public class Account {
    private final int accountNo;
    private final String owner;
    private BigDecimal money;
    private List<Transaction> transactions;

    public Account(int accountNo, String owner) {
        this.accountNo = accountNo;
        this.owner = owner;
        money = BigDecimal.ZERO;
        transactions = new ArrayList<>();
    }

    public int getAccountNo() {
        return accountNo;
    }

    public String getOwner() {
        return owner;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Account copy() {
        Account account = new Account(accountNo, owner);
        account.setMoney(money);
        account.setTransactions(transactions);
        return account;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNo=" + accountNo +
                ", owner='" + owner + '\'' +
                ", money=" + money +
                ", transactions=" + transactions +
                '}';
    }
}
