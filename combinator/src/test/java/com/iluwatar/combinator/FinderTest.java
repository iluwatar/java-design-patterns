package com.iluwatar.combinator;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class FinderTest {

  @Test
  public void contains() {
    var example = "the first one \nthe second one \n";

    var result = Finder.contains("second").find(example);
    Assert.assertEquals(result.size(),1);
    Assert.assertEquals(result.get(0),"the second one ");
  }

}