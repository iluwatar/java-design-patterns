package com.iluwatar;

/**
 * 
 * In Command pattern actions are objects that can
 * be executed and undone. The commands in this example
 * are spells cast by the wizard on the goblin.
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	Wizard wizard = new Wizard();
    	Goblin goblin = new Goblin();

    	goblin.printStatus();
    	
    	wizard.castSpell(new ShrinkSpell(), goblin);
    	goblin.printStatus();
    	
    	wizard.castSpell(new InvisibilitySpell(), goblin);
    	goblin.printStatus();
    	wizard.undoLastSpell();
    	goblin.printStatus();
    }
}
