package com.iluwatar.chain;

/**
 * 
 * The Chain of Responsibility pattern is a design pattern consisting of command 
 * objects and a series of processing objects. Each processing object contains 
 * logic that defines the types of command objects that it can handle; the rest are 
 * passed to the next processing object in the chain. A mechanism also exists for 
 * adding new processing objects to the end of this chain.
 * <p>
 * In this example we organize the request handlers ({@link RequestHandler}) into a
 * chain where each handler has a chance to act on the request on its turn. Here
 * the king ({@link OrcKing}) makes requests and the military orcs ({@link OrcCommander}, 
 * {@link OrcOfficer}, {@link OrcSoldier}) form the handler chain.
 * 
 */
public class App {

	/**
	 * Program entry point
	 * @param args command line args
	 */
	public static void main(String[] args) {

		OrcKing king = new OrcKing();
		king.makeRequest(new Request(RequestType.DEFEND_CASTLE, "defend castle"));
		king.makeRequest(new Request(RequestType.TORTURE_PRISONER,
				"torture prisoner"));
		king.makeRequest(new Request(RequestType.COLLECT_TAX, "collect tax"));

	}
}
