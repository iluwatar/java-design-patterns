package com.iluwatar.filtercache.service;


import com.iluwatar.filtercache.entity.Userinfo;

/**
 *  it defines roughly function.
 */
public interface Userserver {

  /**
     * service class defind some functions.
     *
     * @param id user id.
     * @return related user infomation.
     */
  Userinfo getById(Integer id);


}
