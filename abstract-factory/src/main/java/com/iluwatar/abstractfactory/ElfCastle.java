package com.iluwatar.abstractfactory;

/**
 * 
 * ElfCastle
 *
 */
public class ElfCastle implements Castle {

	static final String DESCRIPTION = "This is the Elven castle!";

	@Override
	public String getDescription() {
		return DESCRIPTION;
	}
}
