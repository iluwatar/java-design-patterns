package com.iluwatar;

public class App 
{
    public static void main( String[] args )
    {
    	Party party = new PartyImpl();
    	Hobbit hobbit = new Hobbit();
    	Wizard wizard = new Wizard();
    	Rogue rogue = new Rogue();
    	Hunter hunter = new Hunter();
    	
    	party.addMember(hobbit);
    	party.addMember(wizard);
    	party.addMember(rogue);
    	party.addMember(hunter);
    	
    	hobbit.act(Action.ENEMY);
    	wizard.act(Action.TALE);
    	rogue.act(Action.GOLD);
    	hunter.act(Action.HUNT);
    }
}
