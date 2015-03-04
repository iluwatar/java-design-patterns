package com.iluwatar;

/**
 * 
 * Null Object implementation for binary tree node.
 *
 */
public class NullNode implements Node {

	@Override
	public int getTreeSize() {
		return 0;
	}

	@Override
	public Node getLeft() {
		return null;
	}

	@Override
	public Node getRight() {
		return null;
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public void walk() {
	}
}
