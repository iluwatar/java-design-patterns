package com.iluwatar.abstractdocument.domain;

import java.util.stream.Stream;

import com.iluwatar.abstractdocument.Document;

public interface HasParts extends Document {
	
	default Stream<Part> getParts() {
		return children("parts", Part::new);
	}

}
