package com.iluwatar.reactor.app;

import java.io.IOException;

import org.junit.Test;

public class AppTest {

	@Test
	public void testApp() throws IOException {
		App app = new App();
		app.start();
		
		AppClient client = new AppClient();
		client.start();
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		client.stop();
		
		app.stop();
	}
}
