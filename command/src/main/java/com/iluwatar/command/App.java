package com.iluwatar.command;

/**
 * 
 * In Command pattern actions are objects that can be executed and undone. 
 * <p>
 * Four terms always associated with the command pattern are command, receiver, invoker and client. A command 
 * object (spell) knows about the receiver (target) and invokes a method of the receiver. Values for parameters of 
 * the receiver method are stored in the command. The receiver then does the work. An invoker object (wizard) 
 * knows how to execute a command, and optionally does bookkeeping about the command execution. The invoker 
 * does not know anything about a concrete command, it knows only about command interface. Both an invoker object 
 * and several command objects are held by a client object (app). The client decides which commands to execute at 
 * which points. To execute a command, it passes the command object to the invoker object.
 * <p>
 * In other words, in this example the wizard casts spells on the goblin. The wizard keeps track of the previous
 * spells cast, so it is easy to undo them. In addition, the wizard keeps track of the spells undone, so they
 * can be redone.
 * 
 * 
 */
public class App {

	/**
	 * Program entry point
	 * @param args command line args
	 */
	public static void main(String[] args) {
		Wizard wizard = new Wizard();
		Goblin goblin = new Goblin();

		goblin.printStatus();

		wizard.castSpell(new ShrinkSpell(), goblin);
		goblin.printStatus();

		wizard.castSpell(new InvisibilitySpell(), goblin);
		goblin.printStatus();

		wizard.undoLastSpell();
		goblin.printStatus();

		wizard.undoLastSpell();
		goblin.printStatus();

		wizard.redoLastSpell();
		goblin.printStatus();

		wizard.redoLastSpell();
		goblin.printStatus();
	}
}
