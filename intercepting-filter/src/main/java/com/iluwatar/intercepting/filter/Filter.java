package com.iluwatar.intercepting.filter;

/**
 * Filters perform certain tasks prior or after execution of
 * request by request handler. In this case, before the request is handled by
 * the target, the request undergoes through each Filter
 * 
 * @author joshzambales
 *
 */
public interface Filter {
	
	/**
	 * Execute order processing filter.
	 * @param order
	 * @return empty string on success, otherwise error message.
	 */
	String execute(Order order);
	
	/**
	 * Set next filter in chain after this.
	 * @param filter
	 */
	void setNext(Filter filter);
	
	/**
	 * Get next filter in chain after this.
	 * @return
	 */
	Filter getNext();
	
	/**
	 * Get last filter in the chain.
	 * @return
	 */
	Filter getLast();
}
