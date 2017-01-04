package com.iluwatar.combinator;

import java.util.function.Function;

public interface ElegantMaskSet extends Function<Warrior, Boolean> {
	static ElegantMaskSet hasGreenGem(){
		return warrior -> warrior.getListOfElegantMasks().contains(ElegantMask.GreenGem);
	}
	
	static ElegantMaskSet hasRedGem(){
		return warrior -> warrior.getListOfElegantMasks().contains(ElegantMask.RedGem);
	}
	
	static ElegantMaskSet hasPurpleGem(){
		return warrior -> warrior.getListOfElegantMasks().contains(ElegantMask.PurpleGem);
	}
	
	default ElegantMaskSet and(ElegantMaskSet other){
		return warrior -> this.apply(warrior) && other.apply(warrior);
	}
	
	
}
