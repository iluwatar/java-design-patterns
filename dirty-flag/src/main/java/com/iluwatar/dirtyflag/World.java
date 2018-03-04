package com.iluwatar.dirtyflag;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * A middle-layer app that calls/passes along data from the back-end.
 * 
 * @author swaisuan
 *
 */
public class World {

  private static World world;
  private static List<String> countries = new ArrayList<String>();

  private World() {
  }

  /**
   * Init.
   * 
   * @return World instance
   */
  public static World getInstance() {
    if (world == null) {
      world = new World();
    }
    return world;
  }

  /**
   * 
   * Calls {@link DataFetcher} to fetch data from back-end.
   * 
   * @return List of strings
   */
  public List<String> fetch() {
    DataFetcher df = DataFetcher.getInstance();
    List<String> data = df.fetch();

    countries = data == null ? countries : data;

    return countries;
  }
}
