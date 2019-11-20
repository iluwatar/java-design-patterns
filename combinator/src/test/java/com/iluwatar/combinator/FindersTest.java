package com.iluwatar.combinator;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static com.iluwatar.combinator.Finders.*;
import static org.junit.Assert.*;

public class FindersTest {

  @Test
  public void advancedFinderTest() {
    var res = advancedFinder("it was","kingdom","sea").find(text());
    Assert.assertEquals(res.size(),1);
    Assert.assertEquals(res.get(0),"It was many and many a year ago,");
  }

  @Test
  public void filteredFinderTest() {
    var res = filteredFinder(" was ", "many", "child").find(text());
    Assert.assertEquals(res.size(),1);
    Assert.assertEquals(res.get(0),"But we loved with a love that was more than love-");
  }

  @Test
  public void specializedFinderTest() {
    var res = specializedFinder("love","heaven").find(text());
    Assert.assertEquals(res.size(),1);
    Assert.assertEquals(res.get(0),"With a love that the winged seraphs of heaven");
  }

  @Test
  public void expandedFinderTest() {
    var res = expandedFinder("It was","kingdom").find(text());
    Assert.assertEquals(res.size(),3);
    Assert.assertEquals(res.get(0),"It was many and many a year ago,");
    Assert.assertEquals(res.get(1),"In a kingdom by the sea,");
    Assert.assertEquals(res.get(2),"In this kingdom by the sea;");
  }


  private String text(){
    return
        "It was many and many a year ago,\n"
            + "In a kingdom by the sea,\n"
            + "That a maiden there lived whom you may know\n"
            + "By the name of ANNABEL LEE;\n"
            + "And this maiden she lived with no other thought\n"
            + "Than to love and be loved by me.\n"
            + "I was a child and she was a child,\n"
            + "In this kingdom by the sea;\n"
            + "But we loved with a love that was more than love-\n"
            + "I and my Annabel Lee;\n"
            + "With a love that the winged seraphs of heaven\n"
            + "Coveted her and me.";
  }

}