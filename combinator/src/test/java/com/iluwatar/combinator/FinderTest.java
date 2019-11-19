package com.iluwatar.combinator;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class FinderTest {

  @Test
  public void contains() {
    String example =
        "the first one \n"
            + "the second one \n"
            + "the second one \n"
            + "the thrid one \n";

    List<String> result = Finder.contains("second").find(example);
    System.out.println("");
  }

  @Test
  public void exact() {
  }

  @Test
  public void not() {
  }

  @Test
  public void or() {
  }

  @Test
  public void and() {
  }
}