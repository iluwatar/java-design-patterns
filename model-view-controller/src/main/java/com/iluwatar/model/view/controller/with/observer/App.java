package com.iluwatar.model.view.controller.with.observer;

/**
 * 
 * In this second example the model-view relationship is different. This time we use the Observer pattern to notify
 * the {@link GiantView} each time the {@link GiantModel} is changed. This way the {@link GiantController} responsibilities
 * are narrowed and it only needs to modify the {@link GiantModel} according to the user input.
 * 
 */
public class App {
	
	/**
	 * Program entry point
	 * @param args command line args
	 */
    public static void main( String[] args ) {
    	// create model, view and controller
    	GiantModel giant = new GiantModel(Health.HEALTHY, Fatigue.ALERT, Nourishment.SATURATED);
    	GiantView view = new GiantView();
    	GiantController controller = new GiantController(giant, view);
    	// initial display
    	controller.updateView();
    	// controller receives some interactions that affect the giant
    	// model modifications trigger the view rendering automatically
    	controller.setHealth(Health.WOUNDED);
    	controller.setNourishment(Nourishment.HUNGRY);
    	controller.setFatigue(Fatigue.TIRED);
    }
}
