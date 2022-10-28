package com.iluwatar.nima;

public class Account {
    public Integer balance;

    public Account(Integer initialBalance){
        balance = initialBalance;
    }

    public Integer balance() {
        return balance;
    }

    public void increase (Integer amount) {
        balance += amount;	}

    public void decrease (Integer amount) {
        balance -= amount;
    }


}
