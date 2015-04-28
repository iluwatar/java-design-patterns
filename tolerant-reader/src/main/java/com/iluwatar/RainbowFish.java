package com.iluwatar;

import java.io.Serializable;

public class RainbowFish implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name;
	private int age;
	private int lengthMeters;
	private int weightTons;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getLengthMeters() {
		return lengthMeters;
	}
	public void setLengthMeters(int lengthMeters) {
		this.lengthMeters = lengthMeters;
	}
	public int getWeightTons() {
		return weightTons;
	}
	public void setWeightTons(int weightTons) {
		this.weightTons = weightTons;
	}
}
