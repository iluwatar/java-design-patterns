package com.iluwatar.model.view.controller;

/**
 * 
 * Model-View-Controller is a pattern for implementing user interfaces. It divides the application
 * into three interconnected parts namely the model, the view and the controller.
 * <p>
 * The central component of MVC, the model, captures the behavior of the application in terms of its problem 
 * domain, independent of the user interface. The model directly manages the data, logic and rules of the 
 * application. A view can be any output representation of information, such as a chart or a diagram
 * The third part, the controller, accepts input and converts it to commands for the model or view.
 * <p>
 * In this example we have a giant ({@link GiantModel}) with statuses for health, fatigue and nourishment. {@link GiantView}
 * can display the giant with its current status. {@link GiantController} receives input affecting the model and
 * delegates redrawing the giant to the view.
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
    	controller.setHealth(Health.WOUNDED);
    	controller.setNourishment(Nourishment.HUNGRY);
    	controller.setFatigue(Fatigue.TIRED);
    	// redisplay
    	controller.updateView();
    }
}
