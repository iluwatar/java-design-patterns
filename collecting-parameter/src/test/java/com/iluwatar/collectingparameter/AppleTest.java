package com.iluwatar.collectingparameter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class AppleTest {
  @Test
  void writeWeightToTest() {
    List<String> info = new ArrayList<>();

    Apple apple = new Apple(10, "a");
    Apple apple1 = new Apple(20, "b");

    apple.writeWeightTo(info);
    apple1.writeWeightTo(info);

    assertEquals("apple weight: 10 \n", info.get(0));
    assertEquals("apple weight: 20 \n", info.get(1));
  }
  @Test
  void writeTypeToTest() {
    List<String> info = new ArrayList<>();

    Apple apple = new Apple(10, "a");
    Apple apple1 = new Apple(20, "b");

    apple.writeTypeTo(info);
    apple1.writeTypeTo(info);

    assertEquals("apple type: a \n", info.get(0));
    assertEquals("apple type: b \n", info.get(1));
  }
}
