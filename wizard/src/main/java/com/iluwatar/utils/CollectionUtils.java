package com.iluwatar.utils;

import java.util.Collection;
import java.util.Iterator;

public class CollectionUtils {

	public static String join(Collection<String> list) {
		Iterator<String> iterator = list.iterator();
		StringBuilder builder = new StringBuilder();
		while (iterator.hasNext()) {
			builder.append(iterator.next());
			if (iterator.hasNext()) {
				builder.append(", ");
			}
		}
		return builder.toString();
	}

}
