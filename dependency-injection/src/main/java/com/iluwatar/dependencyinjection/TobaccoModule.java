package com.iluwatar.dependencyinjection;

import com.google.inject.AbstractModule;

/**
 * 
 * Guice module for binding certain concrete Tobacco implementation.
 *
 */
public class TobaccoModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(Tobacco.class).to(RivendellTobacco.class);
	}
}
