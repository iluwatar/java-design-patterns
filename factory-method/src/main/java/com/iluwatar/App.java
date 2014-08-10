package com.iluwatar;

public class App 
{
    public static void main( String[] args )
    {
    	Blacksmith blacksmith;
    	Weapon weapon;
    	
    	blacksmith = new OrcBlacksmith();
    	weapon = blacksmith.manufactureWeapon(WeaponType.SPEAR);
    	System.out.println(weapon);
    	weapon = blacksmith.manufactureWeapon(WeaponType.AXE);
    	System.out.println(weapon);
    	
    	blacksmith = new ElfBlacksmith();
    	weapon = blacksmith.manufactureWeapon(WeaponType.SHORT_SWORD);
    	System.out.println(weapon);
    	weapon = blacksmith.manufactureWeapon(WeaponType.SPEAR);
    	System.out.println(weapon);
    }
}
