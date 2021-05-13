/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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

package com.iluwatar.caching;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 * Data structure/implementation of the application's cache. The data structure consists of a hash
 * table attached with a doubly linked-list. The linked-list helps in capturing and maintaining the
 * LRU data in the cache. When a data is queried (from the cache), added (to the cache), or updated,
 * the data is moved to the front of the list to depict itself as the most-recently-used data. The
 * LRU data is always at the end of the list.
 */
@Slf4j
public class LruCache {

  static class Node {
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
  Map<String, Node> cache = new HashMap<>();
  Node head;
  Node end;

  public LruCache(int capacity) {
    this.capacity = capacity;
  }

  /**
   * Get user account.
   */
  public UserAccount get(String userId) {
    if (cache.containsKey(userId)) {
      var node = cache.get(userId);
      remove(node);
      setHead(node);
      return node.userAccount;
    }
    return null;
  }

  /**
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
   * Set user account.
   */
  public void set(String userId, UserAccount userAccount) {
    if (cache.containsKey(userId)) {
      var old = cache.get(userId);
      old.userAccount = userAccount;
      remove(old);
      setHead(old);
    } else {
      var newNode = new Node(userId, userAccount);
      if (cache.size() >= capacity) {
        LOGGER.info("# Cache is FULL! Removing {} from cache...", end.userId);
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
   * Invalidate cache for user.
   */
  public void invalidate(String userId) {
    var toBeRemoved = cache.remove(userId);
    if (toBeRemoved != null) {
      LOGGER.info("# {} has been updated! Removing older version from cache...", userId);
      remove(toBeRemoved);
    }
  }

  public boolean isFull() {
    return cache.size() >= capacity;
  }

  public UserAccount getLruData() {
    return end.userAccount;
  }

  /**
   * Clear cache.
   */
  public void clear() {
    head = null;
    end = null;
    cache.clear();
  }

  /**
   * Returns cache data in list form.
   */
  public List<UserAccount> getCacheDataInListForm() {
    var listOfCacheData = new ArrayList<UserAccount>();
    var temp = head;
    while (temp != null) {
      listOfCacheData.add(temp.userAccount);
      temp = temp.next;
    }
    return listOfCacheData;
  }

  /**
   * Set cache capacity.
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
