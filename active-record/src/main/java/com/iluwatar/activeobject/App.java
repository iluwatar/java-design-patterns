package com.iluwatar.activeobject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class App {

	public static void main(String[] args) {
		try {
			DB db = new DB("world", "root", "apple-trunks", "localhost:3306", "city");
			ActiveRow ar = new ActiveRow(db, "101");
			ar.contents = new ArrayList<String>(
					Arrays.asList("101", "Godoy Cruz", "ARG", "Mendoza", "206998"));
			ar.write();
			System.out.println(ar.read());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
