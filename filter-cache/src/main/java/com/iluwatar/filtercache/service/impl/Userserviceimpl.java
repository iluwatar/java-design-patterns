package com.iluwatar.filtercache.service.impl;


import com.iluwatar.filtercache.dao.Userinfodaomybatis;
import com.iluwatar.filtercache.entity.Userinfo;
import com.iluwatar.filtercache.service.Userserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * implement class which connect sql and request.
 */
@Service
public class Userserviceimpl implements Userserver {

  /**
     * initialize the tool class.
     */
  @Autowired
  Userinfodaomybatis userinfodaomybatis;

  /**
     * get user info by id.
     *
     * @param id ask user id.
     * @return related user message.
     */
  @Override
  public Userinfo getById(Integer id) {
    return userinfodaomybatis.getById(id);
  }
}
