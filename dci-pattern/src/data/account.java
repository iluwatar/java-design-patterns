package data;

public class account {
    public int balance;

    public account(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
    // increase balance when get money transferred from other bank accounts
    public void increase (int amount){
        balance+=amount;
    }
    // decrease balance when money moves out the account
    public void decrease (int amount){
        balance=balance - amount;
    }

}
