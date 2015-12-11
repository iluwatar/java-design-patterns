package com.iluwatar.front.controller;

/**
 * 
 * FrontController is the handler class that takes in all the requests and
 * renders the correct response.
 *
 */
public class FrontController {
	
	public void handleRequest(String request) {
		Command command = getCommand(request);
		command.process();
	}
	
	private Command getCommand(String request) {
		Class commandClass = getCommandClass(request);
		try {
			return (Command) commandClass.newInstance();
		} catch (Exception e) {
			throw new ApplicationException(e);
		}
	}
	
	private Class getCommandClass(String request) {
		Class result;
		try {
			result = Class.forName("com.iluwatar.front.controller." + request + "Command");
		} catch (ClassNotFoundException e) {
			result = UnknownCommand.class;
		}
		return result;
	}
}
