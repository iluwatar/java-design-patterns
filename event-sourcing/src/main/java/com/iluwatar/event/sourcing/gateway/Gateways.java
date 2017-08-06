package com.iluwatar.event.sourcing.gateway;

/**
 * Created by serdarh on 06.08.2017.
 */
public class Gateways {
    private static AccountCreateContractSender accountCreateContractSender = new AccountCreateContractSender();
    private static TransactionLogger transactionLogger = new TransactionLogger();

    public static AccountCreateContractSender getAccountCreateContractSender() {
        return accountCreateContractSender;
    }

    public static TransactionLogger getTransactionLogger() {
        return transactionLogger;
    }
}
