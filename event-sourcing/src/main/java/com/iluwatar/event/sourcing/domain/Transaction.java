package com.iluwatar.event.sourcing.domain;

import java.math.BigDecimal;

/**
 * Created by serdarh on 06.08.2017.
 */
public class Transaction {
    private final int accountNo;
    private final BigDecimal moneyIn;
    private final BigDecimal moneyOut;
    private final BigDecimal lastBalance;

    public Transaction(int accountNo, BigDecimal moneyIn, BigDecimal moneyOut, BigDecimal lastBalance) {
        this.accountNo = accountNo;
        this.moneyIn = moneyIn;
        this.moneyOut = moneyOut;
        this.lastBalance = lastBalance;
    }

    public int getAccountNo() {
        return accountNo;
    }

    public BigDecimal getMoneyIn() {
        return moneyIn;
    }

    public BigDecimal getMoneyOut() {
        return moneyOut;
    }

    public BigDecimal getLastBalance() {
        return lastBalance;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "accountNo=" + accountNo +
                ", moneyIn=" + moneyIn +
                ", moneyOut=" + moneyOut +
                ", lastBalance=" + lastBalance +
                '}';
    }
}
