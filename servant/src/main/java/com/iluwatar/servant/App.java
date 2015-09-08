package com.iluwatar.servant;

import java.util.ArrayList;


/**
 * Servant offers some functionality to a group of classes without defining that functionality in each of them.
 * A Servant is a class whose instance provides methods that take care of a desired service,
 * while objects for which the servant does something, are taken as parameters.
 *
 */
public class App {
	
    static Servant jenkins = new Servant("Jenkins");
    static Servant travis = new Servant("Travis");

    /**
     * Program entry point
     * @param args
     */
    public static void main(String[] args) {
        scenario(jenkins, 1);
        scenario(travis, 0);
    }

    /*
     * Can add a List with enum Actions for variable scenarios
     * */
    public static void scenario(Servant servant, int compliment) {
        King k = new King();
        Queen q = new Queen();

        ArrayList<Royalty> guests = new ArrayList<>();
        guests.add(k);
        guests.add(q);

        //feed
        servant.feed(k);
        servant.feed(q);
        //serve drinks
        servant.giveWine(k);
        servant.giveWine(q);
        //compliment
        servant.GiveCompliments(guests.get(compliment));

        //outcome of the night
        for (Royalty r : guests)
            r.changeMood();

        //check your luck
        if (servant.checkIfYouWillBeHanged(guests))
            System.out.println(servant.name + " will live another day");
        else
            System.out.println("Poor " + servant.name + ". His days are numbered");
    }
}
