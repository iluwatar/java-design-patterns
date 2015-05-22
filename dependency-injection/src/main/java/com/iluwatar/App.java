package com.iluwatar;

public class App {
	
    public static void main( String[] args ) {
    	SimpleWizard simpleWizard = new SimpleWizard();
    	simpleWizard.smoke();
    	
    	AdvancedWizard advancedWizard = new AdvancedWizard(new SecondBreakfastTobacco());
    	advancedWizard.smoke();
    }
}
