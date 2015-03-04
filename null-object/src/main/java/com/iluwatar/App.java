package com.iluwatar;

/**
 * 
 * Null Object pattern replaces null values with neutral objects.
 * Many times this simplifies algorithms since no extra null checks
 * are needed.
 * 
 * In this example we build a binary tree where the nodes are either
 * normal or Null Objects. No null values are used in the tree making
 * the traversal easy.
 *
 */
public class App 
{
    public static void main( String[] args ) {
    	
    	Node root = new NodeImpl("1",
    			new NodeImpl("11",
    					new NodeImpl("111",
    							new NullNode(),
    							new NullNode()),
    					new NullNode()), 
    			new NodeImpl("12",
    					new NullNode(),
    					new NodeImpl("122",
    							new NullNode(),
    							new NullNode())));

    	root.walk();
    }
}
