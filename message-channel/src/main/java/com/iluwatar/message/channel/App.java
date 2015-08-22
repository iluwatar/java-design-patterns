package com.iluwatar.message.channel;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class App {
	
	public static void main(String[] args) throws Exception {
		CamelContext context = new DefaultCamelContext();
		
		context.addRoutes(new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				from("direct:greetings").to("stream:out");
			}
		});
		
		context.start();
		context.getRoutes().stream().forEach((r) -> System.out.println(r));
		context.stop();
	}
}
