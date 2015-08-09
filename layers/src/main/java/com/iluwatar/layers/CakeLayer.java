package com.iluwatar.layers;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CakeLayer {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	private int calories;

	public CakeLayer() {
	}

	public CakeLayer(String name, int calories) {
		this.setName(name);
		this.setCalories(calories);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCalories() {
		return calories;
	}

	public void setCalories(int calories) {
		this.calories = calories;
	}
	
	@Override
	public String toString() {
		return String.format("name: %s calories: %d", name, calories);
	}
}
