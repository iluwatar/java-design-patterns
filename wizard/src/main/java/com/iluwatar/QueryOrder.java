package com.iluwatar;

interface QueryOrder extends QueryPagination {
	public QueryPagination orderBy(String column, String... columns);
}
