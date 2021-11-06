package com.iluwatar.caching.database;

/**
 * Creates the database connection accroding the input parameter.
 */
public final class DbManagerFactory {
  /**
   * Private constructor.
   */
  private DbManagerFactory() {
  }

  /**
   * Init database.
   *
   * @param isMongo boolean
   * @return {@link DbManager}
   */
  public static DbManager initDb(final boolean isMongo) {
    if (isMongo) {
      return new MongoDb();
    }
    return new VirtualDb();
  }
}
