package com.iluwatar.dci_pattern.role;

import com.iluwatar.dci_pattern.context.Context;

public class Source_Acc extends Context {
    private Integer money;
    public Object sendTo(){
        if ((Integer) request("Source","Balance")<money){
            System.out.println("Sorry, the funds is insufficient, transaction cancelled");
        }else {
            Object [] obj = {money};
            request("Source","Decrease",obj);
            request("Destination","transferFrom");
        }
        return null;
    }
}
