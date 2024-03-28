package com.iluwatar.activerecord;

import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class RecordBase {

  private final DataSource dataSource;

}
