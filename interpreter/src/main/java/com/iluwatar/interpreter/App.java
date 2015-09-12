package com.iluwatar.interpreter;

import java.util.Stack;

/**
 * 
 * Interpreter pattern breaks sentences into expressions ({@link Expression}) that can
 * be evaluated and as a whole form the result.
 * 
 */
public class App {

	/**
	 * 
	 * Program entry point.
	 * <p>
	 * Expressions can be evaluated using prefix, infix or postfix notations
	 * This sample uses postfix, where operator comes after the operands
	 * 
	 * @param args command line args
	 * 
	 */
	public static void main(String[] args) {
		String tokenString = "4 3 2 - 1 + *";
		Stack<Expression> stack = new Stack<>();

		String[] tokenList = tokenString.split(" ");
		for (String s : tokenList) {
			if (isOperator(s)) {
				Expression rightExpression = stack.pop();
				Expression leftExpression = stack.pop();
				System.out
						.println(String.format(
								"popped from stack left: %d right: %d",
								leftExpression.interpret(),
								rightExpression.interpret()));
				Expression operator = getOperatorInstance(s, leftExpression,
						rightExpression);
				System.out.println(String.format("operator: %s", operator));
				int result = operator.interpret();
				NumberExpression resultExpression = new NumberExpression(result);
				stack.push(resultExpression);
				System.out.println(String.format("push result to stack: %d",
						resultExpression.interpret()));
			} else {
				Expression i = new NumberExpression(s);
				stack.push(i);
				System.out.println(String.format("push to stack: %d",
						i.interpret()));
			}
		}
		System.out
				.println(String.format("result: %d", stack.pop().interpret()));
	}

	public static boolean isOperator(String s) {
		return s.equals("+") || s.equals("-") || s.equals("*");
	}

	public static Expression getOperatorInstance(String s, Expression left,
			Expression right) {
		switch (s) {
		case "+":
			return new PlusExpression(left, right);
		case "-":
			return new MinusExpression(left, right);
		case "*":
			return new MultiplyExpression(left, right);
		}
		return null;
	}
}
