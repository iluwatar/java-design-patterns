/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
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
package com.iluwatar.type.object;

/**
 * 
 * @author Imp92
 */
public class App {
  
  /**
   * Program entry point
   * 
   * @param args command line args
   */
  public static void main(String[] args) {
    
    Breed troll = new Breed(BreedType.TROLL, 20, "Troll attack!");
    
    System.out.println(troll.getHealth());
    
    System.out.println(troll.getAttack());
    
    Breed trollArcher = new Breed(BreedType.TROLL_ARCHER, troll, 10, "TrollArcher attack!");
    
    System.out.println(trollArcher.getType());
    
    System.out.println(trollArcher.getParent().getType());
    
    System.out.println(trollArcher.getHealth());
    
    System.out.println(trollArcher.getAttack());
    
    Breed trollWizard = new Breed(BreedType.TROLL_WIZARD, troll, 30, "TrollWizard attack!");
    
    System.out.println(trollWizard.getType());
    
    System.out.println(trollWizard.getParent().getType());
    
    System.out.println(trollWizard.getHealth());
    
    System.out.println(trollWizard.getAttack());
    
    Breed dragon = new Breed(BreedType.DRAGON, 25, "Dragon attack!");
    
    System.out.println(trollWizard.getHealth());
    
    System.out.println(trollWizard.getAttack());
    
    Breed dragonKnight = new Breed(BreedType.DRAGON, dragon, 0, "Dragon attack!");
    
    System.out.println(trollWizard.getType());
    
    System.out.println(trollWizard.getParent().getType());
    
    System.out.println(trollWizard.getHealth());
    
    System.out.println(trollWizard.getAttack());
    
  }
  
}
