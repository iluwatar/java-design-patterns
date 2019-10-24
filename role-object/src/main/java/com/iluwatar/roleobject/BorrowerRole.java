package com.iluwatar.roleobject;

public class BorrowerRole extends CustomerRole{
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String borrow(){
        return String.join(" ",
                "A borrower",name,"wants to get some money.");
    }

}
