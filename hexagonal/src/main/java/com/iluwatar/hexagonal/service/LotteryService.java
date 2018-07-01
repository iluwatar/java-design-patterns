package com.iluwatar.hexagonal.service;

import com.iluwatar.hexagonal.banking.WireTransfers;

import java.util.Scanner;

public interface LotteryService {
    void checkTicket(com.iluwatar.hexagonal.domain.LotteryService service, Scanner scanner);

    void submitTicket(com.iluwatar.hexagonal.domain.LotteryService service, Scanner scanner);

    void addFundsToLotteryAccount(WireTransfers bank, Scanner scanner);


    void queryLotteryAccountFunds(WireTransfers bank, Scanner scanner);

}
