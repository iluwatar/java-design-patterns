package com.iluwatar.gatekeeper;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class Request {
  String action;
}
