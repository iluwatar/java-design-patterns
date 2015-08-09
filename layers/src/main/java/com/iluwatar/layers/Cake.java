package com.iluwatar.layers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Cake {

	@Id
	@GeneratedValue
	private Long id;
	
	@OneToMany
	private List<CakeLayer> layers;

	@OneToOne
	private CakeTopping topping;
	
	public Cake() {		
		layers = new ArrayList<>();
	}
}
