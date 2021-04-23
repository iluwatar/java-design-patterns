package com.iluwatar.collectingparameter;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CollectingParameterTest {
  @Test
  void mixCollectTest() {
    List<String> info = new ArrayList<>();

    Apple apple = new Apple(10, "a");

    apple.writeWeightTo(info);
    apple.writeTypeTo(info);

    assertEquals("apple weight: 10 \n", info.get(0));
    assertEquals("apple type: a \n", info.get(1));
  }
}
