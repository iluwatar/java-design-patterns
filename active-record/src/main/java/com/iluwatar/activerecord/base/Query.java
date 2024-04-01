package com.iluwatar.activerecord.base;

import java.util.ArrayList;
import java.util.List;

final class Query {

  private Query() {
  }

  static InsertionQuery insertInto(String tableName) {
    return new InsertionQuery(tableName);
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
