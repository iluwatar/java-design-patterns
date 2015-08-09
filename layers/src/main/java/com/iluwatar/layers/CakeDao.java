package com.iluwatar.layers;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CakeDao extends CrudRepository<Cake, Long> {

}
