package com.iluwatar;

public class OldTobyTobacco implements Tobacco {

	@Override
	public void smoke() {
		System.out.println(String.format("Smoking %s", this.getClass().getSimpleName()));
	}
}
