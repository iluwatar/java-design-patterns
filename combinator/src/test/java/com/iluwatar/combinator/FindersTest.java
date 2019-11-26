/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

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