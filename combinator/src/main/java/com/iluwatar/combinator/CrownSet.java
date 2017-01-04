package com.iluwatar.combinator;

import java.util.function.Function;

public interface CrownSet extends Function<Warrior, Boolean> {
	static CrownSet hasRoyalInsignia(){
		return warrior -> warrior.getListOfCrowns().contains(Crown.RoyalInsignia);
	}
	
	static CrownSet hasCrownJewel(){
		return warrior -> warrior.getListOfCrowns().contains(Crown.CrownJewel);
	}
	
	default CrownSet and(CrownSet other){
		return warrior -> this.apply(warrior) && other.apply(warrior);
	}
	
	
}
