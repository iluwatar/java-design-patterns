package com.iluwatar.activerecord;

import static com.iluwatar.activerecord.SchemaConstants.CREATE_SCHEMA_SQL;
import static com.iluwatar.activerecord.SchemaConstants.DB_URL;
import static com.iluwatar.activerecord.SchemaConstants.DELETE_SCHEMA_SQL;

import com.iluwatar.activerecord.base.RecordBase;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

abstract class BaseTest {

  @BeforeEach
  void createSchema() throws SQLException {
    final DataSource dataSource = createDataSource();
    try (Connection conn = DriverManager.getConnection(DB_URL);
         Statement stmt = conn.createStatement()) {
      stmt.execute(CREATE_SCHEMA_SQL);
    }
    RecordBase.setDataSource(dataSource);
  }

  private static DataSource createDataSource() {
    JdbcDataSource dataSource = new JdbcDataSource();
    dataSource.setURL(DB_URL);
    return dataSource;
  }

  @AfterEach
  void deleteSchema() throws SQLException {
    try (Connection conn = DriverManager.getConnection(DB_URL);
         Statement stmt = conn.createStatement()) {
      stmt.execute(DELETE_SCHEMA_SQL);
    }
  }
}
