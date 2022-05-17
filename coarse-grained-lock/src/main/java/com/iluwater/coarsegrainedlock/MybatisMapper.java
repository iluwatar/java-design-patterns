package com.iluwater.coarsegrainedlock;

import com.iluwater.coarsegrainedlock.entity.*;

public interface MybatisMapper {

  int insertCustomer(Customer customer);

  int insertAddress(Address address);
}
