package com.iluwatar.interpreter;

/**
 * 
 * NumberExpression
 *
 */
public class NumberExpression extends Expression {

	private int number;

	public NumberExpression(int number) {
		this.number = number;
	}

	public NumberExpression(String s) {
		this.number = Integer.parseInt(s);
	}

	@Override
	public int interpret() {
		return number;
	}

	@Override
	public String toString() {
		return "number";
	}

}
