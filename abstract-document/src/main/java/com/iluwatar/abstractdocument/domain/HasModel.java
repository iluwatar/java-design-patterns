package com.iluwatar.abstractdocument.domain;

import java.util.Optional;

import com.iluwatar.abstractdocument.Document;

public interface HasModel extends Document {
	
	default Optional<String> getModel() {
		return Optional.ofNullable((String) get("model"));
	}

}
