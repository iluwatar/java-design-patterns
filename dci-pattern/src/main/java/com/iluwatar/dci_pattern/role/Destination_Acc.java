package com.iluwatar.dci_pattern.role;
import com.iluwatar.dci_pattern.context.Context;



public class Destination_Acc extends Context{
    private  Integer money;
    private Context context;

    public Destination_Acc() {

    }

    public Object sendFrom(){
        Object[] obj = {money};
        request("Destination", "Increase",obj);
        return (null);
    }

    private Destination_Acc destination_acc = new Destination_Acc();
}
