package com.iluwatar.gatekeeper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class HackRequest extends Request {
  public HackRequest(String action) {
    super(action);
  }
}
