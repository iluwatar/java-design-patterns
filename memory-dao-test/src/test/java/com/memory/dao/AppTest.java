package com.memory.dao;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.ContextConfiguration;

import com.memory.dao.db.UserDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml")
public class AppTest {

	@Autowired
	private UserDAO dao;
	
	@Before
	public void setUp() {
		System.out.println(String.format("Dao is: %s", dao));
	}
	
	@Test
	public void testApp() {
		assertTrue(true);
	}
}
