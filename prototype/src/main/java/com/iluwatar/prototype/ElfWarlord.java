package com.iluwatar.prototype;

public class ElfWarlord extends Warlord {

	public ElfWarlord() {
	}

	public ElfWarlord(ElfWarlord warlord) {
	}

	@Override
	public Warlord clone() throws CloneNotSupportedException {
		return new ElfWarlord(this);
	}

	@Override
	public String toString() {
		return "Elven warlord";
	}

}
