package com.iluwatar.combinator;

import java.util.function.Function;

public interface ButterFlyLampSet extends Function<Warrior, Boolean> {
	static ButterFlyLampSet hasBlueEye(){
		return warrior -> warrior.getListOfButterflyLamps().contains(ButterflyLamp.BlueEye);
	}
	
	static ButterFlyLampSet hasRedEye(){
		return warrior -> warrior.getListOfButterflyLamps().contains(ButterflyLamp.RedEye);
	}
	
	static ButterFlyLampSet hasGreenEye(){
		return warrior -> warrior.getListOfButterflyLamps().contains(ButterflyLamp.GreenEye);
	}
	
	default ButterFlyLampSet and(ButterFlyLampSet other){
		return warrior -> this.apply(warrior) && other.apply(warrior);
	}
	
	
}
