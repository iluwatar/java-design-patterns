package com.iluwatar.dependency.injection;

import com.google.inject.AbstractModule;

/**
 * 
 * Guice module for binding certain concrete {@link Tobacco} implementation.
 *
 */
public class TobaccoModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(Tobacco.class).to(RivendellTobacco.class);
	}
}
