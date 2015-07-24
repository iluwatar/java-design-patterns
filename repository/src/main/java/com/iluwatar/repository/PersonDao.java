package com.iluwatar.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * 
 * Person repository
 *
 */
@Repository
public interface PersonDao extends CrudRepository<Person, Long> {
	
	public List<Person> findBySurname(String surname);
}
