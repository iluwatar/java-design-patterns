package com.iluwatar;

interface WhereClause extends QueryOrder {
	public QueryFiltering where(String predicate);
}
