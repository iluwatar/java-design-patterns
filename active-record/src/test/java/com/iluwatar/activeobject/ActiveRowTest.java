package com.iluwatar.activeobject;

import com.iluwatar.activeobject.App;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


public class ActiveRowTest {

	@Test
	void initialiseTest() throws SQLException {
			DB db = new DB("world", "root", "apple-trunks", "localhost:3306", "city");
			ActiveRow row = new ActiveRow(db, "1");
			assertDoesNotThrow(row::initialise);
	}


	@Test
	void readRowTest() throws SQLException {
		DB db = new DB("world", "root", "apple-trunks", "localhost:3306", "city");
		ActiveRow row = new ActiveRow(db, "1");

	}

	@Test
	void deleteRowTest() {

	}

@Test
	void insertRowTest() {

	}

}
