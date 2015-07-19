package com.iluwatar;

public class App {
	
	public static void main(String[] args) {
		FrontController controller = new FrontController();
		controller.handleRequest("Archer");
		controller.handleRequest("Catapult");
		controller.handleRequest("foobar");
	}
}
