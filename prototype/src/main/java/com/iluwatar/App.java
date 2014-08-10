package com.iluwatar;

public class App 
{
    public static void main( String[] args )
    {
    	HeroFactory factory;
    	Mage mage;
    	Warlord warlord;
    	Beast beast;
    	
    	factory = new HeroFactoryImpl(new ElfMage(), new ElfWarlord(), new ElfBeast());
    	mage = factory.createMage();
    	warlord = factory.createWarlord();
    	beast = factory.createBeast();
    	System.out.println(mage);
    	System.out.println(warlord);
    	System.out.println(beast);
    	
    	factory = new HeroFactoryImpl(new OrcMage(), new OrcWarlord(), new OrcBeast());
    	mage = factory.createMage();
    	warlord = factory.createWarlord();
    	beast = factory.createBeast();
    	System.out.println(mage);
    	System.out.println(warlord);
    	System.out.println(beast);
    }
}
