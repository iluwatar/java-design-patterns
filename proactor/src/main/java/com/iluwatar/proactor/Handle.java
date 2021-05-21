package com.iluwatar.proactor;

/**
 * Identifies an operating system resource.
 */
public class Handle {
  /* A identity of handler*/
  String handleName = "";

  /**
   * Constructor of Handle.
   *
   * @param handleName name of handle
   */
  Handle(String handleName) {
    this.handleName = handleName;
  }

  /**
   * Constructor of Handle.
   */
  Handle() {
  }
}
