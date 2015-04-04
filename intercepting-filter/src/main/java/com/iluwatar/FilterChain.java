 package com.iluwatar;
 
 import java.util.*;
 
/**
 * Filter Chain carries multiple filters and help to execute them in defined order on target.
 * 
 * @author joshzambales
 */
public class FilterChain {
	private ArrayList<Filter> filters = new ArrayList<Filter>();
	private final Target target;

	public FilterChain(Target target) {
		this.target = target;
	}

	public void addFilter(Filter filter) {
		filters.add(filter);
	}

	public String execute(String request) {
		String tempout[] = new String[filters.size()];

		String tempin[] = request.split("&");
		int i = 0;
		try {
			for (Filter filter : filters) {
				tempout[i] = null;
				tempout[i++] = filter.execute(tempin);
			}
		} catch (Exception e) {
			return "NOT ENOUGHT INPUT";
		}

		if (tempout[4] == null) {
			return "INVALID ORDER!";
		} else if (tempout[3] == null) {
			return "INVALID DEPOSIT NUMBER!";
		} else if (tempout[2] == null) {
			return "INVALID ADRDESS!";
		} else if (tempout[1] == null) {
			return "INVALID Contact Number!";
		} else if (tempout[0] == null) {
			return "INVALID Name!";
		} else {
			target.execute(tempout);
			return "RUNNING...";
		}
	}

}
