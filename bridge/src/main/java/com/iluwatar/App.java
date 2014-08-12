package com.iluwatar;

public class App 
{
    public static void main( String[] args )
    {
    	BlindingMagicWeapon blindingMagicWeapon = new BlindingMagicWeapon(new Excalibur());
    	blindingMagicWeapon.wield();
    	blindingMagicWeapon.blind();
    	blindingMagicWeapon.swing();
    	blindingMagicWeapon.unwield();
    	
    	FlyingMagicWeapon flyingMagicWeapon = new FlyingMagicWeapon(new Mjollnir());
    	flyingMagicWeapon.wield();
    	flyingMagicWeapon.fly();
    	flyingMagicWeapon.swing();
    	flyingMagicWeapon.unwield();
    	
    	SoulEatingMagicWeapon soulEatingMagicWeapon = new SoulEatingMagicWeapon(new Stormbringer());
    	soulEatingMagicWeapon.wield();
    	soulEatingMagicWeapon.swing();
    	soulEatingMagicWeapon.eatSoul();
    	soulEatingMagicWeapon.unwield();
    	
    }
}
