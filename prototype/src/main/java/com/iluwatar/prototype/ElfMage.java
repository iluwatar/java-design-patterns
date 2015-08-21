package com.iluwatar.prototype;

/**
 * 
 * ElfMage
 *
 */
public class ElfMage extends Mage {

	public ElfMage() {
	}

	public ElfMage(ElfMage mage) {
	}

	@Override
	public Mage clone() throws CloneNotSupportedException {
		return new ElfMage(this);
	}

	@Override
	public String toString() {
		return "Elven mage";
	}

}
