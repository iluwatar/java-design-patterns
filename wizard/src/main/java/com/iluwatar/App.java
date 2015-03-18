package com.iluwatar;

/**
 * Construction of some objects are not always as straightforward as filling all setters.
 * Sometimes business rules can be quite restrictive and object can appear in inconsistent state.
 * One solution to this problem is validation but result will be visible only at runtime.
 * Object constructed by Wizard pattern address this issue with "fail fast" approach
 * and trying to expose some initialization problems at compile time.
 *
 * Main disadvantage of Wizard pattern is verbosity and often complicated hierarchy of auxiliary classes and interfaces.
 * Also intermediate return type may be not what is desired (in case you want to save it in some variable
 * and pass into another function for further modification) or predictable as with Builder patter.
 * But when done right (and in right places) can significantly simplify usage of some specific domain API.
 *
 * SqlBuilder wizard try to control flow (or just impose some restrictions) for query initialization process, like:
 * - you can't call anything else than select() after initialization;
 * - final move, toSql(), can be called only if you reach certain point;
 * - if you call orderBy() then you can't call where() or from() afterwards, etc.
 * For all possible "moves" of SqlBuilder please check etc/decisionTree.jpg diagram.
 *
 * For real word examples of usage you can check API of JMock project (v.1), where mockery can be made as chaining of commands:
 * mockObject.expects(once()).method("method_name").withAnyArguments().will(returnValue(true));
 */
public class App {

	public static void main(String[] args) {
		String basicQuery = new SqlBuilder()
			.select("col_1").from("db_1")
			.toSql();
		System.out.println(basicQuery);

		String simpleQuery = new SqlBuilder()
			.select("col_1", "col_11")
				.from("db_1")
			.where("param = 'value'")
				.or("1 = 1")
				.and("param is null")
			.orderBy("col_1")
				.limit(10)
				.startAt(100)
			.toSql();
		System.out.println(simpleQuery);

		String advancedQuery = new SqlBuilder()
			.select("col_1").from("db_1")
			.union()
			.select("col_2").from("db_2").where("1 = 1")
			.unionAll()
			.select("col_3").from("db_3").orderBy("col_3")
			.toSql();
		System.out.println(advancedQuery);
	}
}
