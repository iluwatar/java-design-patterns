package com.iluwatar.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Composite interface.
 * 
 */
public abstract class LetterComposite {

  private List<LetterComposite> children = new ArrayList<>();

  public void add(LetterComposite letter) {
    children.add(letter);
  }

  public int count() {
    return children.size();
  }

  protected abstract void printThisBefore();

  protected abstract void printThisAfter();

  /**
   * Print
   */
  public void print() {
    printThisBefore();
    for (LetterComposite letter : children) {
      letter.print();
    }
    printThisAfter();
  }
}
