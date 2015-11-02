package com.iluwatar.nullobject;

/**
 * 
 * Interface for binary tree node.
 *
 */
public interface Node {

  String getName();

  int getTreeSize();

  Node getLeft();

  Node getRight();

  void walk();
}
