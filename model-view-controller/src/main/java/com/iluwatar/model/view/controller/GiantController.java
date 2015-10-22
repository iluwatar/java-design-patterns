package com.iluwatar.model.view.controller;

/**
 * 
 * GiantController can update the giant data and redraw it using the view.
 *
 */
public class GiantController {

	private GiantModel giant;
	private GiantView view;

	public GiantController(GiantModel giant, GiantView view) {
		this.giant = giant;
		this.view = view;
	}
	
	public Health getHealth() {
		return giant.getHealth();
	}

	public void setHealth(Health health) {
		this.giant.setHealth(health);
	}

	public Fatigue getFatigue() {
		return giant.getFatigue();
	}

	public void setFatigue(Fatigue fatigue) {
		this.giant.setFatigue(fatigue);
	}

	public Nourishment getNourishment() {
		return giant.getNourishment();
	}

	public void setNourishment(Nourishment nourishment) {
		this.giant.setNourishment(nourishment);
	}
	
	public void updateView() {
		this.view.displayGiant(giant);
	}
}
