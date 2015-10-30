package com.iluwatar.front.controller;

/**
 * 
 * The Front Controller is a presentation tier pattern. Essentially it defines a
 * controller that handles all requests for a web site.
 * <p>
 * The Front Controller pattern consolidates request handling through a single handler
 * object ({@link FrontController}). This object can carry out the common the behavior such as
 * authorization, request logging and routing requests to corresponding views.
 * <p>
 * Typically the requests are mapped to command objects ({@link Command}) which then display
 * the correct view ({@link View}).
 * <p>
 * In this example we have implemented two views: {@link ArcherView} and {@link CatapultView}. These
 * are displayed by sending correct request to the {@link FrontController} object. For example,
 * the {@link ArcherView} gets displayed when {@link FrontController} receives request "Archer". When
 * the request is unknown, we display the error view ({@link ErrorView}).
 *
 */
public class App {
	
	/**
	 * Program entry point
	 * @param args command line args
	 */
	public static void main(String[] args) {
		FrontController controller = new FrontController();
		controller.handleRequest("Archer");
		controller.handleRequest("Catapult");
		controller.handleRequest("foobar");
	}
}
