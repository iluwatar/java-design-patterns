package com.iluwatar;

public class App 
{
    public static void main( String[] args )
    {

    	WizardTowerProxy tower = new WizardTowerProxy();
    	tower.enter(new Wizard("Red wizard"));
    	tower.enter(new Wizard("White wizard"));
    	tower.enter(new Wizard("Black wizard"));
    	tower.enter(new Wizard("Green wizard"));
    	tower.enter(new Wizard("Brown wizard"));
        
    }
}
