package data;

import java.util.HashMap;

public class bank {
    // create accountTable, key: accountNumber, value: amount
    public HashMap<Integer,account > accountTable;

    public bank(HashMap<Integer, account> accountTable) {
        this.accountTable = accountTable;
    }

    public HashMap<Integer, account> getAccountTable() {
        return accountTable;
    }

    public void setAccountTable(HashMap<Integer, account> accountTable) {
        this.accountTable = accountTable;
    }

    // create a new bank account
    public void createAccount(Integer accountNumber, Integer amount) {
        accountTable.put(accountNumber, new account(amount));
    }

    // get bank account information
    public account getAccount(Integer accountNumber){
        return accountTable.get(accountNumber);
    }

}
