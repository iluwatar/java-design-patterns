package com.iluwatar.message.channel;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

public class App {
	
	public static void main(String[] args) throws Exception {
		CamelContext context = new DefaultCamelContext();
		context.start();
		Thread.sleep(10000);
		context.stop();
	}
}
