package com.iluwatar.prototype;

public class OrcBeast extends Beast {

	public OrcBeast() {
	}

	public OrcBeast(OrcBeast beast) {
	}

	@Override
	public Beast clone() throws CloneNotSupportedException {
		return new OrcBeast(this);
	}

	@Override
	public String toString() {
		return "Orcish wolf";
	}

}
