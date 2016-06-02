package com.iluwatar.abstractdocument.domain;

import java.util.HashMap;
import java.util.Map;

import com.iluwatar.abstractdocument.AbstractDocument;

public class Car extends AbstractDocument implements HasModel, HasPrice, HasParts {

	protected Car() {
		super(new HashMap<>());
	}
	
	protected Car(Map<String,Object> properties) {
		super(properties);
	}

}
