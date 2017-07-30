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
    
    System.out.println("MonsterType: " + troll.getType());
    
    System.out.println("Health: " + troll.getHealth());
    
    System.out.println("Attack: " + troll.getAttack());
    
    
    Breed trollArcher = new Breed(BreedType.TROLL_ARCHER, troll, 10, "TrollArcher attack!");
    
    System.out.println("MonsterType: " + trollArcher.getType());
    
    System.out.println("MonsterBreed: " + trollArcher.getParent().getType());
    
    System.out.println("Health: " + trollArcher.getHealth());
    
    System.out.println("Attack: " + trollArcher.getAttack());
    
    
    Breed trollWizard = new Breed(BreedType.TROLL_WIZARD, troll, 30, "TrollWizard attack!");
    
    System.out.println("MonsterType: " + trollWizard.getType());
    
    System.out.println("MonsterBreed: " + trollWizard.getParent().getType());
    
    System.out.println("Health: " + trollWizard.getHealth());
    
    System.out.println("Attack: " + trollWizard.getAttack());
    
    
    Breed dragon = new Breed(BreedType.DRAGON, 25, "Dragon attack!");
    
    System.out.println("MonsterType: " + dragon.getType());
    
    System.out.println("Health: " + dragon.getHealth());
    
    System.out.println("Attack: " + dragon.getAttack());
    
    
    Breed dragonKnight = new Breed(BreedType.DRAGON_KNIGHT, dragon, 0, null);
    
    System.out.println("MonsterType: " + dragonKnight.getType());
    
    System.out.println("MonsterBreed: " + dragonKnight.getParent().getType());
    
    System.out.println("Health: " + dragonKnight.getHealth());
    
    System.out.println("Attack: " + dragonKnight.getAttack());
    
    
    Breed giant = new Breed(BreedType.GIANT, 15, "Giant attack!");
    
    new Breed(BreedType.GIANT_CYCLOPS, giant, 12, "Cyclops attack!");
    
    new Breed(BreedType.GIANT_CHIMERA, giant, 17, "Chimera attack!");
    
    Breed beast = new Breed(BreedType.BEAST, 25, "Beast attack!");
    
    new Breed(BreedType.BEAST_HUNTER, beast, 22, "Beast Hunter attack!");
    
  }
  
}
