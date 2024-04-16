package com.iluwatar.activerecord;

/**
 * Database schema related constants.
 */
public final class SchemaConstants {

  private SchemaConstants() {
  }


  public static final String DB_URL = "jdbc:h2:mem:dao;DB_CLOSE_DELAY=-1";

  public static final String CREATE_SCHEMA_SQL = "CREATE TABLE customer\n"
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

  public static final String DELETE_SCHEMA_SQL = "DROP TABLE \"order\";"
      + "DROP TABLE customer";

}
