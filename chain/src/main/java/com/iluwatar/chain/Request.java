package com.iluwatar.chain;

/**
 * 
 * Request
 *
 */
public class Request {

	private String requestDescription;
	private RequestType requestType;

	public Request(RequestType requestType, String requestDescription) {
		this.setRequestType(requestType);
		this.setRequestDescription(requestDescription);
	}

	public String getRequestDescription() {
		return requestDescription;
	}

	public void setRequestDescription(String requestDescription) {
		this.requestDescription = requestDescription;
	}

	public RequestType getRequestType() {
		return requestType;
	}

	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
	}

	@Override
	public String toString() {
		return getRequestDescription();
	}
}
