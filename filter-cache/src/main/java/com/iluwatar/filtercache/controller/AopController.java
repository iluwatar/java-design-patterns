package com.iluwatar.filtercache.controller;


import com.iluwatar.filtercache.Filter;
import com.iluwatar.filtercache.entity.Userinfo;
import com.iluwatar.filtercache.service.impl.Userserviceimpl;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * This is the controller class which receive request, it has doGet and doPost method.
 */
@RestController
public class AopController  implements  Filter {
  /**
   * service class, it define some tool methods.
   */
  @Autowired
  Userserviceimpl tooluser;

  /**
   * real request.
   *
   * @param id ask for user id.
   * @return related user.
   */
  @GetMapping(value = "/gee")
  public Userinfo sayGet(@RequestParam Integer id) {
    return this.doGet(id);
  }

  /**
   * get request deal with.
   *
   * @param map ask for user id.
   * @return related user associted with post request.
   */
  @PostMapping("/pee")
  public Userinfo sayPost(@RequestBody Map<String, String> map) {
    return this.doPost(Integer.parseInt(map.get("id")));
  }

  /**
   * real get request.
   *
   * @param idd ask user id.
   * @return real get request method.
   */
  @Override
  public Userinfo doGet(int idd) {
    return tooluser.getById(idd);
  }

  /**
   * real post request.
   *
   * @param id ask user id.
   * @return real post request method.
   */
  @Override
  public Userinfo doPost(int id) {
    return tooluser.getById(id);
  }
}
