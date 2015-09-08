package com.iluwatar.dependency.injection;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * 
 * Dependency Injection pattern deals with how objects handle their dependencies. The pattern 
 * implements so called inversion of control principle. Inversion of control has two specific rules:
 *  - High-level modules should not depend on low-level modules. Both should depend on abstractions.
 *  - Abstractions should not depend on details. Details should depend on abstractions.
 *  <p>
 *  In this example we show you three different wizards. The first one ({@link SimpleWizard}) is a naive 
 *  implementation violating the inversion of control principle. It depends directly on a concrete
 *  implementation which cannot be changed.
 *  <p>
 *  The second wizard ({@link AdvancedWizard}) is more flexible. It does not depend on any concrete implementation 
 *  but abstraction. It utilizes Dependency Injection pattern allowing its {@link Tobacco} dependency to be
 *  injected through its constructor. This way, handling the dependency is no longer the wizard's
 *  responsibility. It is resolved outside the wizard class.
 *  <p>
 *  The third example takes the pattern a step further. It uses Guice framework for Dependency Injection.
 *  {@link TobaccoModule} binds a concrete implementation to abstraction. Injector is then used to create
 *  {@link GuiceWizard} object with correct dependencies.
 *
 */
public class App {
	
	/**
	 * Program entry point
	 * @param args command line args
	 */
    public static void main( String[] args ) {
    	SimpleWizard simpleWizard = new SimpleWizard();
    	simpleWizard.smoke();
    	
    	AdvancedWizard advancedWizard = new AdvancedWizard(new SecondBreakfastTobacco());
    	advancedWizard.smoke();
    	
    	Injector injector = Guice.createInjector(new TobaccoModule());
    	GuiceWizard guiceWizard = injector.getInstance(GuiceWizard.class);
    	guiceWizard.smoke();
    }
}
