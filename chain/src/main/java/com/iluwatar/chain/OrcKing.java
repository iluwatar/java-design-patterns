package com.iluwatar.chain;

/**
 * 
 * OrcKing makes requests that are handled by the chain.
 * 
 */
public class OrcKing {

	RequestHandler chain;

	public OrcKing() {
		buildChain();
	}

	private void buildChain() {
		chain = new OrcCommander(new OrcOfficer(new OrcSoldier(null)));
	}

	public void makeRequest(Request req) {
		chain.handleRequest(req);
	}

}
