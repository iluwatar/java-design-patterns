package com.iluwatar.layers;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * 
 * CRUD repository for cakes
 *
 */
@Repository
public interface CakeDao extends CrudRepository<Cake, Long> {

}
