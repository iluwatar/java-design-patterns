package com.iluwatar.compositeentity;

/**
 * A specific CoarseGrainedObject to implement a console.
 */

public class ConsoleCoarseGrainedObject extends CoarseGrainedObject<String> {

  DependentObject<String>[] dependentObjects = new DependentObject[]{
      new MessageDependentObject(), new SignalDependentObject()};

  /**
   * A specific setData method, the number of parameters is allowed to be one or two.
   */
  public void setData(String... data) {
    dependentObjects[0].setData(data[0]);
    if (data.length == 2) {
      dependentObjects[1].setData(data[1]);
    }
  }

  public String[] getData() {
    return new String[]{dependentObjects[0].getData(), dependentObjects[1].getData()};
  }
}