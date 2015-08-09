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
		this.name = name;
		this.calories = calories;
	}
}
