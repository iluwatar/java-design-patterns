package com.iluwatar.activerecord;

import com.iluwatar.activerecord.base.RecordBase;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;

abstract class BaseTest {

  @BeforeEach
  void setUp() throws SQLException {
    final DataSource dataSource = createDataSource();
    createSchema(dataSource);
    RecordBase.setDataSource(dataSource);
  }

  private static final String DB_URL = "jdbc:h2:mem:dao;DB_CLOSE_DELAY=-1";

  private static final String CREATE_SCHEMA_SQL = "CREATE TABLE customer\n"
      + "(\n"
      + "    id              BIGINT      NOT NULL,\n"
      + "    customerNumber VARCHAR(15) NOT NULL,\n"
      + "    firstName      VARCHAR(45) NOT NULL,\n"
      + "    lastName       VARCHAR(45) NOT NULL,\n"
      + "    CONSTRAINT customer_pkey PRIMARY KEY (id)\n"
      + ");\n"
      + "\n"
      + "CREATE TABLE \"order\"\n"
      + "(\n"
      + "    id           BIGINT      NOT NULL,\n"
      + "    orderNumber VARCHAR(15) NOT NULL,\n"
      + "    customerId  BIGINT      NOT NULL,\n"
      + "    CONSTRAINT order_pkey PRIMARY KEY (id),\n"
      + "    CONSTRAINT customer_id_fk FOREIGN KEY (customerId) REFERENCES customer (id)\n"
      + ")";

  private static void createSchema(DataSource dataSource) throws SQLException {
    try (Connection conn = dataSource.getConnection();
         Statement stmt = conn.createStatement()) {
      stmt.execute(CREATE_SCHEMA_SQL);
    }
  }

  private static DataSource createDataSource() {
    JdbcDataSource dataSource = new JdbcDataSource();
    dataSource.setURL(DB_URL);
    return dataSource;
  }
}
