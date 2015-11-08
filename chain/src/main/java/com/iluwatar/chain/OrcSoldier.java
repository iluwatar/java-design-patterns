package com.iluwatar.chain;

/**
 * 
 * OrcSoldier
 *
 */
public class OrcSoldier extends RequestHandler {

	public OrcSoldier(RequestHandler handler) {
		super(handler);
	}

	@Override
	public void handleRequest(Request req) {
		if (req.getRequestType().equals(RequestType.COLLECT_TAX)) {
			printHandling(req);
		} else {
			super.handleRequest(req);
		}
	}

	@Override
	public String toString() {
		return "Orc soldier";
	}
}
