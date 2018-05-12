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

  private List<String> countries;
  private DataFetcher df;

  public World() {
    this.countries = new ArrayList<String>();
    this.df = new DataFetcher();
  }

  /**
   * 
   * Calls {@link DataFetcher} to fetch data from back-end.
   * 
   * @return List of strings
   */
  public List<String> fetch() {
    List<String> data = df.fetch();

    countries = data.isEmpty() ? countries : data;

    return countries;
  }
}
