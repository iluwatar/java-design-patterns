package com.iluwatar.state;

/**
 * 
 * Peaceful state.
 *
 */
public class PeacefulState implements State {

	private Mammoth mammoth;

	public PeacefulState(Mammoth mammoth) {
		this.mammoth = mammoth;
	}

	@Override
	public void observe() {
		System.out.println(String.format("%s is calm and peaceful.", mammoth));
	}

	@Override
	public void onEnterState() {
		System.out.println(String.format("%s calms down.", mammoth));
	}

}
