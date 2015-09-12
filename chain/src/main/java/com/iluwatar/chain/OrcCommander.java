package com.iluwatar.chain;

/**
 * 
 * OrcCommander
 *
 */
public class OrcCommander extends RequestHandler {

	public OrcCommander(RequestHandler handler) {
		super(handler);
	}

	@Override
	public void handleRequest(Request req) {
		if (req.getRequestType().equals(RequestType.DEFEND_CASTLE)) {
			printHandling(req);
		} else {
			super.handleRequest(req);
		}
	}

	@Override
	public String toString() {
		return "Orc commander";
	}
}
