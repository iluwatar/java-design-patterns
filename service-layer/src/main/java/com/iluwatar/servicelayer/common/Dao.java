package com.iluwatar.servicelayer.common;

import java.util.List;

/**
 * 
 * Dao interface.
 *
 * @param <E>
 * 
 */
public interface Dao<E extends BaseEntity> {

	E find(Long id);
	
	void persist(E entity);
	
	E merge(E entity);
	
	void delete(E entity);
	
	List<E> findAll();
}
