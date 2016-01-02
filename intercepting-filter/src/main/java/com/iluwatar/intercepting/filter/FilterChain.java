package com.iluwatar.intercepting.filter;


/**
 * Filter Chain carries multiple filters and help to execute them in defined order on target.
 * 
 * @author joshzambales
 */
public class FilterChain {

  private Filter chain;

  /**
   * Constructor
   */
  public FilterChain() {
  }

  /**
   * Adds filter
   */
  public void addFilter(Filter filter) {
    if (chain == null) {
      chain = filter;
    } else {
      chain.getLast().setNext(filter);
    }
  }

  /**
   * Execute filter chain
   */
  public String execute(Order order) {
    if (chain != null) {
      return chain.execute(order);
    } else {
      return "RUNNING...";
    }
  }
}
