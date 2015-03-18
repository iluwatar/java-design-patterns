package com.iluwatar;

interface QueryPagination extends QueryClosure {
	public QueryPagination limit(int limit);
	public QueryPagination startAt(int startAt);
}
