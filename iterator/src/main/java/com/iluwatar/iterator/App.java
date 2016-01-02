package com.iluwatar.iterator;

/**
 * 
 * The Iterator pattern is a design pattern in which an iterator is used to traverse a container and
 * access the container's elements. The Iterator pattern decouples algorithms from containers.
 * <p>
 * In this example the Iterator ({@link ItemIterator}) adds abstraction layer on top of a collection
 * ({@link TreasureChest}). This way the collection can change its internal implementation without
 * affecting its clients.
 * 
 */
public class App {

  /**
   * Program entry point
   * 
   * @param args command line args
   */
  public static void main(String[] args) {
    TreasureChest chest = new TreasureChest();

    ItemIterator ringIterator = chest.iterator(ItemType.RING);
    while (ringIterator.hasNext()) {
      System.out.println(ringIterator.next());
    }

    System.out.println("----------");

    ItemIterator potionIterator = chest.iterator(ItemType.POTION);
    while (potionIterator.hasNext()) {
      System.out.println(potionIterator.next());
    }

    System.out.println("----------");

    ItemIterator weaponIterator = chest.iterator(ItemType.WEAPON);
    while (weaponIterator.hasNext()) {
      System.out.println(weaponIterator.next());
    }

    System.out.println("----------");

    ItemIterator it = chest.iterator(ItemType.ANY);
    while (it.hasNext()) {
      System.out.println(it.next());
    }
  }
}
