package com.iluwatar.abstractdocument.domain;

import java.util.Optional;

import com.iluwatar.abstractdocument.Document;

public interface HasPrice extends Document {
	
	default Optional<Number> getPartner() {
		return Optional.ofNullable((Number) get("price"));
	}

}
