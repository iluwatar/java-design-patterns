package com.iluwatar.interpreter;

/**
 * 
 * MinusExpression
 *
 */
public class MinusExpression extends Expression {

	private Expression leftExpression;
	private Expression rightExpression;

	public MinusExpression(Expression leftExpression, Expression rightExpression) {
		this.leftExpression = leftExpression;
		this.rightExpression = rightExpression;
	}

	@Override
	public int interpret() {
		return leftExpression.interpret() - rightExpression.interpret();
	}

	@Override
	public String toString() {
		return "-";
	}

}
