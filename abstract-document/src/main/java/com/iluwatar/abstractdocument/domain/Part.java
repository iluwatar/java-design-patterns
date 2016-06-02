package com.iluwatar.abstractdocument.domain;

import java.util.HashMap;
import java.util.Map;

import com.iluwatar.abstractdocument.AbstractDocument;

public class Part extends AbstractDocument implements HasModel, HasPrice {

	protected Part() {
		super(new HashMap<>());
	}
	
	protected Part(Map<String, Object> properties) {
		super(properties);
	}

}
