package com.Iluwatar.combinator;

import java.util.function.Function;

public interface BeersteinSet extends Function<Warrior, Boolean> {
	static BeersteinSet hasGreenCatseye(){
		return warrior -> warrior.getListOfElegantMasks().contains(Beerstein.GreenCatseye);
	}
	
	static BeersteinSet hasRedCatseye(){
		return warrior -> warrior.getListOfElegantMasks().contains(Beerstein.RedCatseye);
	}
	
	static BeersteinSet hasYellowCatseye(){
		return warrior -> warrior.getListOfElegantMasks().contains(Beerstein.YellowCatseye);
	}
	
	default BeersteinSet and(BeersteinSet other){
		return warrior -> this.apply(warrior) && other.apply(warrior);
	}
	
	
}
