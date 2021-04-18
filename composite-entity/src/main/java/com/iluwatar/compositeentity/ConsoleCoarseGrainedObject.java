package com.iluwatar.compositeentity;

/**
 * A specific CoarseGrainedObject to implement a console.
 */

public class ConsoleCoarseGrainedObject extends CoarseGrainedObject<String> {

  DependentObject<String>[] consoleDependentObjects = new DependentObject[]{
      new MessageDependentObject(), new SignalDependentObject()};

  /**
   * A specific setData method, the number of parameters is allowed to be one or two.
   */
  @Override
  public void setData(String... data) {
    consoleDependentObjects[0].setData(data[0]);
    if (data.length == 2) {
      consoleDependentObjects[1].setData(data[1]);
    }
  }

  @Override
  public String[] getData() {
    return new String[]{consoleDependentObjects[0].getData(), consoleDependentObjects[1].getData()};
  }
}