package com.iluwatar.monostate;

public class App {
	public static void main(String[] args) {
		LoadBalancer loadBalancer1 = new LoadBalancer();
		LoadBalancer loadBalancer2 = new LoadBalancer();
		loadBalancer1.serverequest(new Request("Hello"));
		loadBalancer2.serverequest(new Request("Hello World"));
	}

}
