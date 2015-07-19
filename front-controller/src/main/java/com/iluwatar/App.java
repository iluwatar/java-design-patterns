package com.iluwatar;

/**
 * 
 * The Front Controller is a presentation tier pattern. Essentially it defines a
 * controller that handles all requests for a web site.
 * 
 * The Front Controller pattern consolidates request handling through a single handler
 * object (FrontController). This object can carry out the common the behavior such as
 * authorization, request logging and routing requests to corresponding views.
 * 
 * Typically the requests are mapped to command objects (Command) which then display
 * the correct view (View).
 * 
 * In this example we have implemented two views: ArcherView and CatapultView. These
 * are displayed by sending correct request to the FrontController object. For example,
 * the ArcherView gets displayed when FrontController receives request "Archer". When
 * the request is unknown, we display the error view (ErrorView).
 *
 */
public class App {
	
	public static void main(String[] args) {
		FrontController controller = new FrontController();
		controller.handleRequest("Archer");
		controller.handleRequest("Catapult");
		controller.handleRequest("foobar");
	}
}
