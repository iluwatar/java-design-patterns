package com.iluwatar.combinator;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;

public class WarriorTest {
	
	@Test
	public void testEquipment() throws Exception{
		
		/*
		 *  Test if the warrior was correctly created and test if validation is correct.
		 */
		Warrior warrior = new Warrior(Arrays.asList(Crown.CrownJewel, Crown.RoyalInsignia),
				Arrays.asList(ButterflyLamp.BlueEye, ButterflyLamp.GreenEye, ButterflyLamp.RedEye),
				Arrays.asList(Beerstein.GreenCatseye),
				Arrays.asList(ElegantMask.PurpleGem),
				Arrays.asList(GoldenLynx.BlueStoneOfTreason, GoldenLynx.GreenStoneOfJudgement, GoldenLynx.RedStoneOfFaith));
		assertNotNull(warrior);
		assertNotNull(warrior.toString());
		assertEquals(true, CrownSet.hasCrownJewel().and(CrownSet.hasRoyalInsignia()).apply(warrior));
		assertEquals(true, ButterFlyLampSet.hasBlueEye()
				.and(ButterFlyLampSet.hasGreenEye()
				.and(ButterFlyLampSet.hasRedEye()))
				.apply(warrior));
		assertEquals(false, ElegantMaskSet.hasGreenGem().apply(warrior));
		assertEquals(true, ElegantMaskSet.hasPurpleGem().apply(warrior));
		assertEquals(true, GoldenLynxSet.hasBlueStoneOfTreason()
				.and(GoldenLynxSet.hasGreenStoneOfJudgement())
				.and(GoldenLynxSet.hasRedStoneOfFaith())
				.apply(warrior));
		
	}
}
