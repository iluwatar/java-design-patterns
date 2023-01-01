package com.iluwatar.context.object;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Where context objects are defined.
 */
@ToString
@Getter
@Setter
public class ServiceContext {

  String accountService;
  String sessionService;
  String searchService;
}
