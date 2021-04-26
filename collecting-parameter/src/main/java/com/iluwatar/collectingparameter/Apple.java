package com.iluwatar.collectingparameter;

import java.util.List;

/**
 * Apple, the class need to collect information from.
 */
public class Apple {
  int weight;
  String type;

  public Apple(int weight, String type) {
    this.weight = weight;
    this.type = type;
  }

  public void writeWeightTo(List<String> weightInfo) {
    String appleWeight = "apple weight: " + weight + " \n";
    weightInfo.add(appleWeight);
  }

  public void writeTypeTo(List<String> typeInfo) {
    String appleType = "apple type: " + type + " \n";
    typeInfo.add(appleType);
  }
}
