package com.iluwatar.interpreter;

/**
 * 
 * MultiplyExpression
 *
 */
public class MultiplyExpression extends Expression {

	private Expression leftExpression;
	private Expression rightExpression;

	public MultiplyExpression(Expression leftExpression,
			Expression rightExpression) {
		this.leftExpression = leftExpression;
		this.rightExpression = rightExpression;
	}

	@Override
	public int interpret() {
		return leftExpression.interpret() * rightExpression.interpret();
	}

	@Override
	public String toString() {
		return "*";
	}

}
