package com.iluwatar.caching;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * Data structure/implementation of the application's cache. The data structure consists of a hash
 * table attached with a doubly linked-list. The linked-list helps in capturing and maintaining the
 * LRU data in the cache. When a data is queried (from the cache), added (to the cache), or updated,
 * the data is moved to the front of the list to depict itself as the most-recently-used data. The
 * LRU data is always at the end of the list.
 *
 */
public class LRUCache {

  class Node {
    String userID;
    UserAccount userAccount;
    Node previous;
    Node next;

    public Node(String userID, UserAccount userAccount) {
      this.userID = userID;
      this.userAccount = userAccount;
    }
  }

  int capacity;
  HashMap<String, Node> cache = new HashMap<String, Node>();
  Node head = null;
  Node end = null;

  public LRUCache(int capacity) {
    this.capacity = capacity;
  }

  public UserAccount get(String userID) {
    if (cache.containsKey(userID)) {
      Node node = cache.get(userID);
      remove(node);
      setHead(node);
      return node.userAccount;
    }
    return null;
  }

  /**
   *
   * Remove node from linked list.
   */
  public void remove(Node node) {
    if (node.previous != null) {
      node.previous.next = node.next;
    } else {
      head = node.next;
    }
    if (node.next != null) {
      node.next.previous = node.previous;
    } else {
      end = node.previous;
    }
  }

  /**
   *
   * Move node to the front of the list.
   */
  public void setHead(Node node) {
    node.next = head;
    node.previous = null;
    if (head != null)
      head.previous = node;
    head = node;
    if (end == null)
      end = head;
  }

  public void set(String userID, UserAccount userAccount) {
    if (cache.containsKey(userID)) {
      Node old = cache.get(userID);
      old.userAccount = userAccount;
      remove(old);
      setHead(old);
    } else {
      Node newNode = new Node(userID, userAccount);
      if (cache.size() >= capacity) {
        System.out.println("# Cache is FULL! Removing " + end.userID + " from cache...");
        cache.remove(end.userID); // remove LRU data from cache.
        remove(end);
        setHead(newNode);
      } else {
        setHead(newNode);
      }
      cache.put(userID, newNode);
    }
  }

  public boolean contains(String userID) {
    return cache.containsKey(userID);
  }

  public void invalidate(String userID) {
    System.out.println("# " + userID + " has been updated! Removing older version from cache...");
    Node toBeRemoved = cache.get(userID);
    remove(toBeRemoved);
    cache.remove(userID);
  }

  public boolean isFull() {
    return cache.size() >= capacity;
  }

  public UserAccount getLRUData() {
    return end.userAccount;
  }

  public void clear() {
    head = null;
    end = null;
    cache.clear();
  }

  /**
   *
   * Returns cache data in list form.
   */
  public ArrayList<UserAccount> getCacheDataInListForm() {
    ArrayList<UserAccount> listOfCacheData = new ArrayList<UserAccount>();
    Node temp = head;
    while (temp != null) {
      listOfCacheData.add(temp.userAccount);
      temp = temp.next;
    }
    return listOfCacheData;
  }

  public void setCapacity(int newCapacity) {
    if (capacity > newCapacity) {
      clear(); // Behavior can be modified to accommodate for decrease in cache size. For now, we'll
               // just clear the cache.
    } else {
      this.capacity = newCapacity;
    }
  }
}
