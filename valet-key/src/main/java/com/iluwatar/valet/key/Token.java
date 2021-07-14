package com.iluwatar.valet.key;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Token {
  private final boolean valid;
  private final int target;
}
