package com.iluwatar;

interface QueryClosure {
	public String toSql();
	public SelectClause union();
	public SelectClause unionAll();
}
