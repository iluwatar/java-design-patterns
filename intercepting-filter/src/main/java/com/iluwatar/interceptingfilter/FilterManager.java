package com.iluwatar.interceptingfilter;

/**
 * Filter Manager manages the filters and Filter Chain.
 * 
 * @author joshzambales
 *
 */
public class FilterManager {
	
	private FilterChain filterChain;

	public FilterManager(Target target) {
		filterChain = new FilterChain(target);
	}

	public void addFilter(Filter filter) {
		filterChain.addFilter(filter);
	}

	public String filterRequest(Order order) {
		return filterChain.execute(order);
	}
}
