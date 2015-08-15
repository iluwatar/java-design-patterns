package com.iluwatar.layers;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * 
 * CRUD repository for cake layers
 *
 */
@Repository
public interface CakeLayerDao extends CrudRepository<CakeLayer, Long> {

}
