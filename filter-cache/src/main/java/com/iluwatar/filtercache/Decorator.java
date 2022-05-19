package com.iluwatar.filtercache;

import com.iluwatar.filtercache.entity.Userinfo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;


/**
 * Number 2 filter.
 */
@Aspect
@Component
@Order(2)
public class Decorator implements Filter {
  /**
   * real get filter.
   *
   * @param id user id.
   * @return get user info.
   */
  @Override
  public Userinfo doGet(int id) {
    if (id > 10) {
      return new Userinfo(id, "id more than 10", "id more than 10", 0);
    }
    return null;
  }

  /**
   * real post request.
   *
   * @param id post id.
   * @return post user info.
   */
  @Override
  public Userinfo doPost(int id) {
    if (id > 5) {
      return new Userinfo(id, "id more than 5", "id more than 5", 0);
    }
    return null;
  }

  @Pointcut("execution (* com.iluwatar.filtercache.controller.AopController.*(..))")
  public void deal() {

  }

  /**
   * number 2 cache deal with get request.
   *
   * @param proceedingJoinPoint request.
   * @return user info.
   * @throws IOException not find the user info.
   */
  @Around("deal()")
  public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws IOException {
    String id;
    Userinfo proceed = null;
    Userinfo userinfo;
    if (proceedingJoinPoint.getSignature().getName().equals("sayGet")) {
      id = proceedingJoinPoint.getArgs()[0].toString();
      userinfo = this.doGet(Integer.parseInt(id));
    } else {
      HashMap<String, String> tt = ( HashMap<String, String> ) proceedingJoinPoint.getArgs()[0];
      id = tt.get("id");
      userinfo = this.doPost(Integer.parseInt(id));
    }
    if (userinfo != null) {
      return userinfo;
    } else {

      try {
        proceed = (Userinfo) proceedingJoinPoint.proceed();
      } catch (Throwable t) {
        t.printStackTrace();
      }

    }
    return proceed;
  }
}