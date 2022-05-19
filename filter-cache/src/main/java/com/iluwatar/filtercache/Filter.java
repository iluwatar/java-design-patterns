package com.iluwatar.filtercache;

import com.iluwatar.filtercache.entity.Userinfo;

/**
 * Filter class define some request function.
 */
public interface Filter {
  /**
   * get defind.
   *
   * @param id user id.
   * @return related user info.
   */
  Userinfo doGet(int id);

  /**
   * post defind.
   *
   * @param id user id.
   * @return related post user info.
   */
  Userinfo doPost(int id);

}
