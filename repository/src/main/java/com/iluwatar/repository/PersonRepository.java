package com.iluwatar.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * 
 * Person repository
 *
 */
@Repository
public interface PersonRepository
    extends CrudRepository<Person, Long>, JpaSpecificationExecutor<Person> {

  Person findByName(String name);
}
