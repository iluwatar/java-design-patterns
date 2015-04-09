 package com.iluwatar;
 
  
/**
 * Filter Chain carries multiple filters and help to execute them in defined order on target.
 * 
 * @author joshzambales
 */
public class FilterChain {
	
	private Filter chain;
	
	private final Target target;

	public FilterChain(Target target) {
		this.target = target;
	}

	public void addFilter(Filter filter) {
		if (chain == null) {
			chain = filter;
		} else {
			chain.getLast().setNext(filter);
		}
	}

	public String execute(Order order) {

		if (chain != null) {
			return chain.execute(order);
		} else {
			return "RUNNING...";
		}
		
//		String tempout[] = new String[filters.size()];
//
//		String tempin[] = request.split("&");
//		int i = 0;
//		try {
//			for (Filter filter : filters) {
//				tempout[i] = null;
//				tempout[i++] = filter.execute(tempin);
//			}
//		} catch (Exception e) {
//			return "NOT ENOUGHT INPUT";
//		}
//
//		if (tempout[4] == null) {
//			return "INVALID ORDER!";
//		} else if (tempout[3] == null) {
//			return "INVALID DEPOSIT NUMBER!";
//		} else if (tempout[2] == null) {
//			return "INVALID ADRDESS!";
//		} else if (tempout[1] == null) {
//			return "INVALID Contact Number!";
//		} else if (tempout[0] == null) {
//			return "INVALID Name!";
//		} else {
//			target.execute(tempout);
//			return "RUNNING...";
//		}
	}
}
