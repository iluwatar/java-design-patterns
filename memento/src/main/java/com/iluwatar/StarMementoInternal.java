package com.iluwatar;

/**
 * 
 * Internal interface to memento.
 *
 */
public class StarMementoInternal implements StarMemento {

	private StarType type;
	private int ageYears;
	private int massTons;
	
	public StarType getType() {
		return type;
	}
	public void setType(StarType type) {
		this.type = type;
	}
	public int getAgeYears() {
		return ageYears;
	}
	public void setAgeYears(int ageYears) {
		this.ageYears = ageYears;
	}
	public int getMassTons() {
		return massTons;
	}
	public void setMassTons(int massTons) {
		this.massTons = massTons;
	}
}
