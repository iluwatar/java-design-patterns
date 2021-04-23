package com.iluwatar.queryobject;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the raw data source. In real scenarios it can
 * be a table in a database.
 *
 * @param <T> The type of rows in this table.
 */

public class Dataset<T> {
  public final List<T> entities;

  public Dataset(List<T> entities) {
    this.entities = entities == null ? new ArrayList<>() : entities;
  }
}
