package com.iluwatar;

interface SelectClause {
	public FromClause select(String column, String... columns);
}
