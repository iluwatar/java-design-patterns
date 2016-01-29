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
public class LruCache {

  class Node {
    String userId;
    UserAccount userAccount;
    Node previous;
    Node next;

    public Node(String userId, UserAccount userAccount) {
      this.userId = userId;
      this.userAccount = userAccount;
    }
  }

  int capacity;
  HashMap<String, Node> cache = new HashMap<>();
  Node head = null;
  Node end = null;

  public LruCache(int capacity) {
    this.capacity = capacity;
  }

  /**
   * Get user account
   */
  public UserAccount get(String userId) {
    if (cache.containsKey(userId)) {
      Node node = cache.get(userId);
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
    if (head != null) {
      head.previous = node;
    }
    head = node;
    if (end == null) {
      end = head;
    }
  }

  /**
   * Set user account
   */
  public void set(String userId, UserAccount userAccount) {
    if (cache.containsKey(userId)) {
      Node old = cache.get(userId);
      old.userAccount = userAccount;
      remove(old);
      setHead(old);
    } else {
      Node newNode = new Node(userId, userAccount);
      if (cache.size() >= capacity) {
        System.out.println("# Cache is FULL! Removing " + end.userId + " from cache...");
        cache.remove(end.userId); // remove LRU data from cache.
        remove(end);
        setHead(newNode);
      } else {
        setHead(newNode);
      }
      cache.put(userId, newNode);
    }
  }

  public boolean contains(String userId) {
    return cache.containsKey(userId);
  }

  /**
   * Invalidate cache for user
   */
  public void invalidate(String userId) {
    System.out.println("# " + userId + " has been updated! Removing older version from cache...");
    Node toBeRemoved = cache.get(userId);
    remove(toBeRemoved);
    cache.remove(userId);
  }

  public boolean isFull() {
    return cache.size() >= capacity;
  }

  public UserAccount getLruData() {
    return end.userAccount;
  }

  /**
   * Clear cache
   */
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
    ArrayList<UserAccount> listOfCacheData = new ArrayList<>();
    Node temp = head;
    while (temp != null) {
      listOfCacheData.add(temp.userAccount);
      temp = temp.next;
    }
    return listOfCacheData;
  }

  /**
   * Set cache capacity
   */
  public void setCapacity(int newCapacity) {
    if (capacity > newCapacity) {
      clear(); // Behavior can be modified to accommodate for decrease in cache size. For now, we'll
               // just clear the cache.
    } else {
      this.capacity = newCapacity;
    }
  }
}
