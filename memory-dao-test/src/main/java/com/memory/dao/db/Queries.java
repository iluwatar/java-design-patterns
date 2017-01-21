package com.memory.dao.db;

public enum Queries {
	
	GET_USER("SELECT * FROM users WHERE name = :name"),
	GET_ALL_USERS("SELECT * FROM users")
	;
	
	private final String query;
	
	Queries(final String query) {
		this.query = query;
	}
	
	public String get() {
		return this.query;
	}

}
