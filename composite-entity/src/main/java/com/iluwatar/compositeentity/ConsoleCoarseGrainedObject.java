package com.iluwatar.compositeentity;

/**
 * A specific CoarseGrainedObject to implement a console.
 */

public class ConsoleCoarseGrainedObject extends CoarseGrainedObject<String> {

  /**
   * A specific setData method, the number of parameters is allowed to be one or two.
   */
  @Override
  public void setData(String... data) {
    super.setData(data);
  }

  @Override
  public String[] getData() {
    super.getData();
    return new String[]{
        dependentObjects[0].getData(), dependentObjects[1].getData()
    };
  }

  public void init() {
    dependentObjects = new DependentObject[]{
        new MessageDependentObject(), new SignalDependentObject()};
  }
}