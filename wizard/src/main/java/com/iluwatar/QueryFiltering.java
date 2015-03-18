package com.iluwatar;

interface QueryFiltering extends QueryOrder {
	public QueryFiltering and(String predicate);
	public QueryFiltering or(String predicate);
}
