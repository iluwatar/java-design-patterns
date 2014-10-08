package com.iluwatar;

public class ShrinkSpell extends Command {

	private Size oldSize;

	private Target target;

	public ShrinkSpell() {
		oldSize = null;
		target = null;
	}

	@Override
	public void execute(Target target) {
		oldSize = target.getSize();
		target.setSize(Size.SMALL);
		this.target = target;
	}

	@Override
	public void undo() {
		if (oldSize != null && target != null) {
			target.setSize(oldSize);
		}
	}

	@Override
	public String toString() {
		return "Shrink spell";
	}
}
