package com.iluwatar;



public class App {
    public static void main( String[] args ) {   
    	WizardDaoImpl dao = new WizardDaoImpl();
    	persistData(dao);
    	queryData();
    }
    
    public static void persistData(WizardDaoImpl dao) {
    	Spell spell = new Spell("Fireball");
    	Spellbook spellbook = new Spellbook("Book of fire");
    	spell.setSpellbook(spellbook);
    	spellbook.getSpells().add(spell);
    	Wizard wizard = new Wizard("Jugga");
    	spellbook.setWizard(wizard);
    	wizard.getSpellbooks().add(spellbook);
    	dao.persist(wizard);
    }
    
    public static void queryData() {
    	MagicService magicService = new MagicServiceImpl();
    	for (Wizard w: magicService.findAllWizards()) {
    		System.out.println(w);
    	}
    }
}
