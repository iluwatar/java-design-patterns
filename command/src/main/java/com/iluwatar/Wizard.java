package com.iluwatar;

public class Wizard extends Target {

	private Command previousSpell;

	public Wizard() {
		setSize(Size.NORMAL);
		setVisibility(Visibility.VISIBLE);
	}

	public void castSpell(Command command, Target target) {
		System.out.println(this + " casts " + command + " at " + target);
		command.execute(target);
		previousSpell = command;
	}

	public void undoLastSpell() {
		if (previousSpell != null) {
			System.out.println(this + " undoes " + previousSpell);
			previousSpell.undo();
		}
	}

	@Override
	public String toString() {
		return "Wizard";
	}

}
