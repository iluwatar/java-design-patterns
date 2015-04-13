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
    	spell.setSpellbook(spellbook);
    	spellbook.getSpells().add(spell);
    	Wizard wizard = new Wizard("Jugga");
    	spellbook.setWizard(wizard);
    	wizard.getSpellbooks().add(spellbook);
    	dao.persist(wizard);
    }
    
    public static void queryData(WizardDao dao) {
    	List<Wizard> wizards = dao.findAll();
    	for (Wizard w: wizards) {
    		System.out.println(w);
    		for (Spellbook spellbook: w.getSpellbooks()) {
    			System.out.println(spellbook);
    			for (Spell spell: spellbook.getSpells()) {
    				System.out.println(spell);
    			}
    		}
    	}
    }
}
