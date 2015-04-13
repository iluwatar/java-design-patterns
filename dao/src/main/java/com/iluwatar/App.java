package com.iluwatar;

import java.util.List;


public class App {
    public static void main( String[] args ) {   
    	WizardDao dao = new WizardDao();
    	persistData(dao);
    	queryData(dao);
    }
    
    public static void persistData(WizardDao dao) {
    	Spell spell = new Spell("Fireball");
    	Spellbook spellbook = new Spellbook("Book of fire");
    	spellbook.getSpells().add(spell);
    	Wizard wizard = new Wizard("Jugga");
    	wizard.getSpellbooks().add(spellbook);
    	dao.persist(wizard);
    }
    
    public static void queryData(WizardDao dao) {
    	List<Wizard> wizards = dao.findAll();
    	for (Wizard w: wizards) {
    		System.out.println(w);
    	}
    }
}
