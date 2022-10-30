package com.iluwatar.activeobject;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class ActiveRowTest {

	@Test
	void initialiseTest() {
		DB db = new DB("world", "root", "apple-trunks", "localhost:3306", "city");
		ActiveRow row = new ActiveRow(db, "1");
		assertDoesNotThrow(row::initialise);
	}


	@Test
	void readRowTest() {
		DB db = new DB("world", "root", "apple-trunks", "localhost:3306", "city");
		ActiveRow row = new ActiveRow(db, "1");
		if (!(row.read().equals(new ArrayList<String>()))) {
			for (int i = 0; i < row.read().size(); i++) {
				assertNotNull(row.read().get(i));
			}
		}

	}

	@Test
	void deleteRowTest() {
		DB db = new DB("world", "root", "apple-trunks", "localhost:3306", "city");
		ActiveRow row = new ActiveRow(db, "1");
		row.delete();
		assertEquals(row.read(), new ArrayList<String>());

	}

	@Test
	void insertRowTest() {
		DB db = new DB("world", "root", "apple-trunks", "localhost:3306", "city");
		ActiveRow row = new ActiveRow(db, "1");
		row.delete();
		row.contents = new ArrayList<String>(
				Arrays.asList("101", "Godoy Cruz", "ARG", "Mendoza", "206998"));
		assertEquals(row.read(), Arrays.asList("101", "Godoy Cruz", "ARG", "Mendoza", "206998"));

	}

}
