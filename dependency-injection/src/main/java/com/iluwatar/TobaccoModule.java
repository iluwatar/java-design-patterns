package com.iluwatar;

import com.google.inject.AbstractModule;

public class TobaccoModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(Tobacco.class).to(RivendellTobacco.class);
	}
}
