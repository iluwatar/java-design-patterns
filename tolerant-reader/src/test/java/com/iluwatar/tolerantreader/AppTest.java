package com.iluwatar.tolerantreader;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.iluwatar.tolerantreader.App;

/**
 * 
 * Application test
 *
 */
public class AppTest {
	
	@Test
	public void test() throws ClassNotFoundException, IOException {
		String[] args = {};
		App.main(args);
	}
	
	@Before
	@After
	public void cleanup() {
		File file1 = new File("fish1.out");
		file1.delete();
		File file2 = new File("fish2.out");
		file2.delete();
	}	
}
