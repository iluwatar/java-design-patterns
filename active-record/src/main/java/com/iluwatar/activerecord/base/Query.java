package com.iluwatar.activerecord.base;

import java.util.ArrayList;
import java.util.List;

final class Query {

  private Query() {
  }

  static SelectionQuery selectFrom(String tableName) {
    return new SelectionQuery(tableName);
  }

  static InsertionQuery insertInto(String tableName) {
    return new InsertionQuery(tableName);
  }

  static class SelectionQuery {
    private final String table;
    private final List<String> columns = new ArrayList<>();
    private final List<String> whereKeys = new ArrayList<>();

    SelectionQuery(String table) {
      this.table = table;
    }

    @Override
    public String toString() {
      return "SELECT "
          + (!columns.isEmpty() ? String.join(",", columns) : "*")
          + " FROM "
          + table
          + constructWhere(whereKeys);
    }

    SelectionQuery where(String column) {
      whereKeys.add(column);
      return this;
    }

    private String constructWhere(List<String> whereKeys) {
      if (whereKeys.isEmpty()) {
        return "";
      }

      return constructWhereFromMap(whereKeys);
    }

    private static String constructWhereFromMap(List<String> whereKeys) {
      StringBuilder whereClause = new StringBuilder(" WHERE ");
      for (String key : whereKeys) {
        whereClause.append(key)
            .append(" = ")
            .append("?");
      }
      return whereClause.toString();
    }
  }

  static class InsertionQuery {

    private final String table;
    private final List<String> columns = new ArrayList<>();
    private final List<String> values = new ArrayList<>();

    InsertionQuery(String table) {
      this.table = table;
    }

    @Override
    public String toString() {
      return "INSERT INTO "
          + table
          + " ("
          + String.join(",", columns)
          + ") VALUES ("
          + String.join(",", values)
          + ")";
    }

    InsertionQuery column(String column) {
      columns.add(column);
      return this;
    }

    InsertionQuery value(String value) {
      values.add(value);
      return this;
    }
  }

}
