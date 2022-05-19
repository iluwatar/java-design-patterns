package com.iluwatar.filtercache.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * entity class, a return type.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Userinfo {

  /**
     * user id.
     */
  private  Integer id;
  /**
     * user password.
     */
  private  String password;
  /**
     * user name.
     */
  private  String username;
  /**
     * user's location.
     */
  private  Integer city;
}
