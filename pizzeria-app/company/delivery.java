
package com.company;
import java.util.List;
import java.util.Scanner;

public class delivery {

    public List<methode> listeitem;
    public deliveryStrategy strategy;

    public delivery(List<methode> listeitem, deliveryStrategy strategy) {
        this.listeitem = listeitem;
        this.strategy = strategy;
    }

    public List<methode> getListeitem() {
        return listeitem;
    }



    public void setListeitem(List<methode> listeitem) {
        this.listeitem = listeitem;
    }

    public deliveryStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(deliveryStrategy strategy) {
        this.strategy = strategy;
    }


    public void handle(methode message){
        if (message.type == "Delivery to Home"){
            strategy = new DeliveryToHome();
        }
        if (message.type == "Collection in person"){
            strategy = new CollectionInPerson();
        }
        strategy.dologic();

    }


}
