package com.iluwatar.gatekeeper;

import lombok.Getter;
import lombok.Setter;

/**
 * Hack request with dangerous request.
 */
@Getter
@Setter

public class HackRequest extends Request {
  public HackRequest(String action) {
    super(action);
  }
}
