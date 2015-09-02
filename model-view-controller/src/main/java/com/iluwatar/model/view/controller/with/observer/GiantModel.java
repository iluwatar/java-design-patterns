package com.iluwatar.model.view.controller.with.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * GiantModel contains the giant data.
 *
 */
public class GiantModel {
	
	private Health health;
	private Fatigue fatigue;
	private Nourishment nourishment;
	private List<GiantModelObserver> observers = new ArrayList<>();

	GiantModel(Health health, Fatigue fatigue, Nourishment nourishment) {
		this.health = health;
		this.fatigue = fatigue;
		this.nourishment = nourishment;	
	}

	public Health getHealth() {
		return health;
	}

	public void setHealth(Health health) {
		this.health = health;
		notifyObservers();
	}

	public Fatigue getFatigue() {
		return fatigue;
	}

	public void setFatigue(Fatigue fatigue) {
		this.fatigue = fatigue;
		notifyObservers();
	}

	public Nourishment getNourishment() {
		return nourishment;
	}

	public void setNourishment(Nourishment nourishment) {
		this.nourishment = nourishment;
		notifyObservers();
	}
	
	@Override
	public String toString() {
		return String.format("The giant looks %s, %s and %s.", health, fatigue, nourishment);
	}
	
	public void registerObserver(GiantModelObserver observer) {
		observers.add(observer);
	}
	
	private void notifyObservers() {
		observers.stream().forEach((GiantModelObserver o) -> o.modelChanged(this));
	}
}
