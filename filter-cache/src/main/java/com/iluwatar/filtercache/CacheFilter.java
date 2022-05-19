package com.iluwatar.filtercache;


import cn.hutool.json.JSONObject;
import com.iluwatar.filtercache.entity.Userinfo;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;



/**
 * Cache,if there're messages related to request,then return directly.
 */
@Aspect
@Component
@Order(1)
public class CacheFilter implements Filter {
  /**
   * Cache structure.
   */
  private final HashMap<String, Userinfo> hashMap = new HashMap<>();

  @Pointcut("execution (* com.iluwatar.filtercache.controller.AopController.*(..))")
  public void test() {
  }

  /**
   * first cache.
   *
   * @param proceedingJoinPoint aop request.
   * @return user info
   * @throws IOException some exception related to no data in database.
   */
  @Around("test()")
  public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws IOException {
    System.out.println("before");
    String id = proceedingJoinPoint.getArgs()[0].toString();
    JSONObject jsonObject;
    Userinfo proceed = null;
    Userinfo userinfo;
    if (proceedingJoinPoint.getSignature().getName().equals("sayGet")) {
      userinfo = this.doGet(Integer.parseInt(id));
    } else {
      id = id.substring(id.length() - 2, id.length() - 1);
      userinfo = this.doPost(Integer.parseInt(id));
    }
    if (userinfo != null) {

      return userinfo;
    }
    if (hashMap.containsKey(id)) {
      jsonObject = new JSONObject();
      Map<String, String> a = new HashMap<>();
      Userinfo now = hashMap.get(id);
      a.put("id", String.valueOf(now.getId()));
      a.put("city", String.valueOf(now.getCity()));
      a.put("password", now.getPassword());
      a.put("username", now.getUsername());
      jsonObject.putAll(a);
      return hashMap.get(id);
    }
    try {
      proceed = (Userinfo) proceedingJoinPoint.proceed();
    } catch (Throwable t) {
      t.printStackTrace();
    }
    if (proceed != null) {
      Userinfo m = new Userinfo();
      m.setId(proceed.getId());
      m.setCity(proceed.getCity());
      m.setPassword(proceed.getPassword());
      m.setUsername(proceed.getUsername());
      hashMap.put(id, m);
    }
    System.out.println("after");
    return proceed;
  }

  /**
   * this is doget method.
   *
   * @param id user id.
   * @return user info.
   */
  @Override
  public Userinfo doGet(int id) {
    if (id > 12) {
      return new Userinfo(id, "id more than 10", "id more than 12", 0);
    }
    return null;
  }

  /**
   * deal with post request.
   *
   * @param id post user id.
   * @return user info.
   */
  @Override
  public Userinfo doPost(int id) {
    if (id > 10) {
      return new Userinfo(id, "id more than 5", "id more than 10", 0);
    }
    return null;
  }
}