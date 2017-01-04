package com.Iluwatar.combinator;

import java.util.function.Function;

public interface GoldenLynxSet extends Function<Warrior, Boolean> {
	static GoldenLynxSet hasBlueStoneOfTreason(){
		return warrior -> warrior.getListOfGoldenLynxes().contains(GoldenLynx.BlueStoneOfTreason);
	}
	
	static GoldenLynxSet hasGreenStoneOfJudgement(){
		return warrior -> warrior.getListOfGoldenLynxes().contains(GoldenLynx.GreenStoneOfJudgement);
	}
	
	static GoldenLynxSet hasRedStoneOfFaith(){
		return warrior -> warrior.getListOfGoldenLynxes().contains(GoldenLynx.RedStoneOfFaith);
	}
	
	default GoldenLynxSet and(GoldenLynxSet other){
		return warrior -> this.apply(warrior) && other.apply(warrior);
	}
}
