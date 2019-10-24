package com.iluwatar.roleobject;

public class InvestorRole extends CustomerRole {
    private String name;
    private long amountToInvest;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getAmountToInvest() {
        return amountToInvest;
    }

    public void setAmountToInvest(long amountToInvest) {
        this.amountToInvest = amountToInvest;
    }

    public String invest() {
        return String.join(" ",
                "Investor", name, "has invested", String.valueOf(amountToInvest), "dollars");
    }
}
