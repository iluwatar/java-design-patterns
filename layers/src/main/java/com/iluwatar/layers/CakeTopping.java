package com.iluwatar.layers;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CakeTopping {

	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	private int calories;
	
	public CakeTopping() {
	}
	
	public CakeTopping(String name, int calories) {
		this.name = name;
		this.calories = calories;
	}
}
