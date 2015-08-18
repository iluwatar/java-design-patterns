package com.iluwatar.chain;

/**
 * 
 * Chain of Responsibility organizes request handlers ({@link RequestHandler}) into a
 * chain where each handler has a chance to act on the request on its turn. In
 * this example the king ({@link OrcKing}) makes requests and the military orcs
 * ({@link OrcCommander}, {@link OrcOfficer}, {@link OrcSoldier}) form the handler chain.
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
