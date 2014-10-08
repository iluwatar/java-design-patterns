package com.iluwatar;

public class InvisibilitySpell extends Command {

	private Target target;

	public InvisibilitySpell() {
		target = null;
	}

	@Override
	public void execute(Target target) {
		target.setVisibility(Visibility.INVISIBLE);
		this.target = target;
	}

	@Override
	public void undo() {
		if (target != null) {
			target.setVisibility(Visibility.VISIBLE);
		}
	}

	@Override
	public String toString() {
		return "Invisibility spell";
	}
}
