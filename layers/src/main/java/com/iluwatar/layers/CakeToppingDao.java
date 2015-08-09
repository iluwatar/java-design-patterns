package com.iluwatar.layers;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CakeToppingDao extends CrudRepository<CakeTopping, Long> {

}
