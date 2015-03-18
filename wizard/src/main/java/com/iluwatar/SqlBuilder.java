package com.iluwatar;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import com.iluwatar.utils.CollectionUtils;

public class SqlBuilder implements SelectClause {

	private String _db;
	private String _wherePredicate;
	private Integer _limit;
	private Integer _startAt;
	private List<String> _selectColumns = new ArrayList<>();
	private List<String> _orderByColumns = new ArrayList<>();
	private List<Entry<String, String>> _predicates = new ArrayList<>();

	private String _sql = "";

	@Override
	public FromClause select(String column, String... columns) {
		_selectColumns.add(column);
		_selectColumns.addAll(Arrays.asList(columns));
		return new FromClauseImpl();
	}

	private String build() {
		return new StringBuilder()
			.append(selectClause())
			.append(fromClause())
			.append(whereClause())
			.append(orderBy())
			.append(pagination())
			.toString();
	}

	private String selectClause() {
		return "SELECT " + CollectionUtils.join(_selectColumns);
	}

	private String fromClause() {
		return " FROM " + _db;
	}

	private String whereClause() {
		if (_wherePredicate == null) {
			return "";
		}
		StringBuilder builder = new StringBuilder();
		builder.append(" WHERE ").append(_wherePredicate);
		for (Entry<String, String> entry : _predicates) {
			builder.append(String.format(" %s %s", entry.getKey(), entry.getValue()));
		}
		return builder.toString();
	}

	private String orderBy() {
		if (_orderByColumns.isEmpty()) {
			return "";
		}
		return " ORDER BY " + CollectionUtils.join(_orderByColumns);
	}

	private String pagination() {
		StringBuilder pagination = new StringBuilder();
		if (_limit != null) {
			pagination.append(" LIMIT ").append(_limit);
		}
		if (_startAt != null) {
			pagination.append(" OFFSET ").append(_startAt);
		}
		return pagination.toString();
	}

	private class QueryClosureImpl implements QueryClosure {
		@Override
		public String toSql() {
			return _sql + SqlBuilder.this.build();
		}

		@Override
		public SelectClause union() {
			return union("UNION");
		}

		@Override
		public SelectClause unionAll() {
			return union("UNION ALL");
		}

		private SelectClause union(String unionOperator) {
			_sql += SqlBuilder.this.build() + String.format(" %s ", unionOperator);
			resetSqlBuilderState();
			return SqlBuilder.this;
		}

		private void resetSqlBuilderState() {
			SqlBuilder.this._db = null;
			SqlBuilder.this._wherePredicate = null;
			SqlBuilder.this._limit = null;
			SqlBuilder.this._startAt = null;
			SqlBuilder.this._selectColumns.clear();
			SqlBuilder.this._orderByColumns.clear();
			SqlBuilder.this._predicates.clear();
		}
	}

	private class QueryPagionationImpl extends QueryClosureImpl implements QueryPagination {
		@Override
		public QueryPagination limit(int limit) {
			_limit = limit;
			return new QueryPagionationImpl();
		}
		@Override
		public QueryPagination startAt(int startAt) {
			_startAt = startAt;
			return new QueryPagionationImpl();
		}
	}

	private class QueryOrderImpl extends QueryPagionationImpl implements QueryOrder {
		@Override
		public QueryPagination orderBy(String column, String... columns) {
			_orderByColumns.add(column);
			_orderByColumns.addAll(Arrays.asList(columns));
			return new QueryPagionationImpl();
		}
	}

	private class QueryFilteringImpl extends QueryOrderImpl implements QueryFiltering {
		@Override
		public QueryFiltering and(String predicate) {
			_predicates.add(new AbstractMap.SimpleEntry<String, String>("AND", predicate));
			return new QueryFilteringImpl();
		}
		@Override
		public QueryFiltering or(String predicate) {
			_predicates.add(new AbstractMap.SimpleEntry<String, String>("OR", predicate));
			return new QueryFilteringImpl();
		}
	}

	private class WhereClauseImpl extends QueryOrderImpl implements WhereClause {
		@Override
		public QueryFiltering where(String predicate) {
			_wherePredicate = predicate;
			return new QueryFilteringImpl();
		}
	}

	private class FromClauseImpl implements FromClause {
		@Override
		public WhereClause from(String db) {
			_db = db;
			return new WhereClauseImpl();
		}
	}
}
