package com.iluwatar;

/**
 * The Main class is the entry point for the application that demonstrates
 * financial account operations using the Account, Currency, and Money classes.
 * It creates accounts, performs deposit and allocation operations, and
 * displays the account balances.
 */
public final class App {
    private App() {
        // Private constructor to hide the default public constructor
    }
    /**
     * Entry point of the application.
     * @param args Command-line arguments.
     */
    public static void main(final String[] args) {
        final Currency usd = Currency.usd();
        final Currency eur = Currency.eur();

        final Money money1 = new Money(10_000, usd);
        final Money money2 = new Money(5_000, eur);
        final int amount1 = 70;
        final int amount2 = 30;


        final Account account = new Account(usd, eur);

        account.deposit(money1);
        account.deposit(money2);

        System.out.println("Primary Balance: "
                + account.getPrimaryBalance().getAmount()
                + " "
                + account.getPrimaryBalance().getCurrency().
                getStringRepresentation());
        System.out.println("Secondary Balance: "
                + account.getSecondaryBalance().getAmount()
                + " "
                + account.getSecondaryBalance().getCurrency().
                getStringRepresentation());

        final Money allocationMoney = new Money(6_000, usd);

        final Account[] accounts = new Account[2];
        accounts[0] = account;
        accounts[1] = new Account(usd, eur);

        allocationMoney.allocate(accounts, amount1,
                amount2);

        System.out.println("Allocated Balances:");
        for (int i = 0; i < accounts.length; i++) {
            System.out.println("Account "
                    + (i + 1)
                    + ": " + accounts[i].getPrimaryBalance().getAmount()
                    + " "
                    + accounts[i].getPrimaryBalance().getCurrency().
                    getStringRepresentation());
        }
    }
}
