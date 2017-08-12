package com.iluwatar.event.sourcing.domain;

import com.iluwatar.event.sourcing.event.AccountCreateEvent;
import com.iluwatar.event.sourcing.event.MoneyDepositEvent;
import com.iluwatar.event.sourcing.event.MoneyTransferEvent;
import com.iluwatar.event.sourcing.event.MoneyWithdrawalEvent;
import com.iluwatar.event.sourcing.gateway.Gateways;
import com.iluwatar.event.sourcing.state.AccountAggregate;

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

    private Transaction depositMoney(BigDecimal money) {
        this.money = this.money.add(money);
        Transaction transaction = new Transaction(accountNo,money,BigDecimal.ZERO,this.money);
        transactions.add(transaction);
        return transaction;
    }

    private Transaction withdrawMoney(BigDecimal money) {
        this.money = this.money.subtract(money);
        Transaction transaction = new Transaction(accountNo,BigDecimal.ZERO,money,this.money);
        transactions.add(transaction);
        return transaction;
    }

    private void handleDeposit(BigDecimal money,boolean realTime) {
        Transaction transaction = depositMoney(money);
        AccountAggregate.putAccount(this);
        if(realTime) {
            Gateways.getTransactionLogger().log(transaction);
        }
    }

    private void handleWithdrawal(BigDecimal money, boolean realTime) {
        if(this.money.compareTo(money)==-1){
            throw new RuntimeException("Insufficient Account Balance");
        }

        Transaction transaction = withdrawMoney(money);
        AccountAggregate.putAccount(this);
        if(realTime) {
            Gateways.getTransactionLogger().log(transaction);
        }
    }

    public void handleEvent(MoneyDepositEvent moneyDepositEvent) {
        handleDeposit(moneyDepositEvent.getMoney(),moneyDepositEvent.isRealTime());
    }


    public void handleEvent(MoneyWithdrawalEvent moneyWithdrawalEvent) {
        handleWithdrawal(moneyWithdrawalEvent.getMoney(),moneyWithdrawalEvent.isRealTime());
    }


    public void handleTransferFromEvent(MoneyTransferEvent moneyTransferEvent) {
        handleWithdrawal(moneyTransferEvent.getMoney(),moneyTransferEvent.isRealTime());
    }

    public void handleTransferToEvent(MoneyTransferEvent moneyTransferEvent) {
        handleDeposit(moneyTransferEvent.getMoney(),moneyTransferEvent.isRealTime());
    }

    public void handleEvent(AccountCreateEvent accountCreateEvent) {
        AccountAggregate.putAccount(this);
        // check if this event is replicated from journal before calling an external gateway function
        if(accountCreateEvent.isRealTime()) {
            Gateways.getAccountCreateContractSender().sendContractInfo(this);
        }
    }
}
