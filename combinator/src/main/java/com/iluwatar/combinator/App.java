package com.iluwatar.combinator;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App{
	static Logger Logger = LoggerFactory.getLogger(App.class);
	
	public static void main(String[] args) {
		Warrior warrior = new Warrior(Arrays.asList(Crown.CrownJewel),
				Arrays.asList(ButterflyLamp.BlueEye, ButterflyLamp.GreenEye),
				Arrays.asList(Beerstein.GreenCatseye),
				Arrays.asList(ElegantMask.PurpleGem),
				Arrays.asList(GoldenLynx.BlueStoneOfTreason, GoldenLynx.GreenStoneOfJudgement, GoldenLynx.RedStoneOfFaith));
		Logger.info(ButterFlyLampSet.hasBlueEye()
				.and(ButterFlyLampSet.hasGreenEye()
				.and(ButterFlyLampSet.hasRedEye()))
				.apply(warrior).toString());
		
		Logger.info(BeersteinSet.hasRedCatseye().apply(warrior).toString());
		
		Logger.info(GoldenLynxSet.hasBlueStoneOfTreason()
				.and(GoldenLynxSet.hasGreenStoneOfJudgement())
				.and(GoldenLynxSet.hasRedStoneOfFaith()).apply(warrior).toString());
		
		
		

	}
}
