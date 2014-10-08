package com.iluwatar;

/**
 * 
 * Star uses "mementos" to store and restore state.
 * 
 */
public class Star {

	private StarType type;
	private int ageYears;
	private int massTons;

	public Star(StarType startType, int startAge, int startMass) {
		this.type = startType;
		this.ageYears = startAge;
		this.massTons = startMass;
	}

	public void timePasses() {
		ageYears *= 2;
		massTons *= 8;
		switch (type) {
		case RED_GIANT:
			type = StarType.WHITE_DWARF;
			break;
		case SUN:
			type = StarType.RED_GIANT;
			break;
		case SUPERNOVA:
			type = StarType.DEAD;
			break;
		case WHITE_DWARF:
			type = StarType.SUPERNOVA;
			break;
		case DEAD:
			ageYears *= 2;
			massTons = 0;
			break;
		default:
			break;
		}
	}

	StarMemento getMemento() {

		StarMementoInternal state = new StarMementoInternal();
		state.setAgeYears(ageYears);
		state.setMassTons(massTons);
		state.setType(type);
		return state;

	}

	void setMemento(StarMemento memento) {

		StarMementoInternal state = (StarMementoInternal) memento;
		this.type = state.getType();
		this.ageYears = state.getAgeYears();
		this.massTons = state.getMassTons();

	}

	@Override
	public String toString() {
		return String.format("%s age: %d years mass: %d tons", type.toString(),
				ageYears, massTons);
	}
}
