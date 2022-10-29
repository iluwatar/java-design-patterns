package com.iluwatar.dependentmapping;

import java.sql.Connection;

/**
 * Some objects naturally appear in the context of other objects. Whenever you load or save the base album, you can load
 * or save the tracks in the album. If these tracks are not referenced by any other table in the database,
 * you can simplify the mapping process by having the album mapper perform track mapping and treat this mapping
 * as a related mapping.
 *
 * <p>In this example, I just use the basic method of dependent-mapping to show an example described above.
 * I have pre-installed some database related methods. However, due to miss of database, to realize them here has not been
 * done.
 */
public class App {

  /**
   * Return the basic "Select" sentence of SQL.
   *
   * @param args not args need.
   */
  public static void main(String[] args) {
    Connection connection = null;
    AlbumMapper albumMapper = new AlbumMapper(connection);
    String str = albumMapper.findstatement();
    System.out.println(str);
  }
}
