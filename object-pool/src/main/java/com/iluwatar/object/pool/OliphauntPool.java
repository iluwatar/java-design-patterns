package com.iluwatar.object.pool;

/**
 * 
 * Oliphaunt object pool
 *
 */
public class OliphauntPool extends ObjectPool<Oliphaunt> {

	@Override
	protected Oliphaunt create() {
		return new Oliphaunt();
	}
}
