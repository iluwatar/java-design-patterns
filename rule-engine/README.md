---
title: "Rule Engine Pattern in Java: Replacing Tangled Conditionals with Composable Rules"
shortTitle: Rule Engine
description: "Learn the Rule Engine design pattern in Java. Encapsulate each business rule as an independent object and let an engine evaluate and execute them against a shared context, replacing large nested if/else blocks with small, testable, composable rules."
category: Behavioral
language: en
tag:
  - Business
  - Decoupling
  - Domain
  - Encapsulation
  - Extensibility
---

## Intent of Rule Engine Design Pattern

Encapsulate each business rule as an independent, self-contained object and let an engine evaluate and execute a collection of those rules against a shared context, so that decision logic can grow and change without rewriting a large nested conditional block.

## Detailed Explanation of Rule Engine Pattern with Real-World Examples

Real-world example

> A bank decides whether to approve a loan by checking several independent criteria: the applicant must be old enough, earn enough, and have a good enough credit score. Instead of hard-coding one enormous condition, the bank keeps each criterion as a separate policy on a checklist. A clerk walks the whole checklist for every application, ticks off the criteria that pass, and writes down the ones that fail. Adding a new criterion means adding a line to the checklist, not rewriting the whole approval procedure.

In plain words

> The Rule Engine pattern turns each branch of a giant `if/else` into its own object and hands a collection of them to an engine that runs them all against the same input, collecting which passed and which failed.

## Programmatic Example of Rule Engine Pattern in Java

Each rule separates the **decision** (`evaluate`) from the **action** (`execute`). `evaluate` reports whether a rule's condition holds; `execute` performs the side effect associated with a satisfied rule.

```java
public interface Rule<T> {

  String name();

  boolean evaluate(T context);

  void execute(T context);
}
```

The context is an immutable value object that carries the data the rules inspect.

```java
public record LoanApplication(
    int age, double monthlyIncome, double loanAmount, int creditScore) {}
```

Concrete rules encapsulate one criterion each. New criteria are added by writing new classes — the engine never changes.

```java
public class MinimumAgeRule implements Rule<LoanApplication> {

  private final int minimumAge;

  public MinimumAgeRule(int minimumAge) {
    this.minimumAge = minimumAge;
  }

  @Override
  public String name() {
    return "MinimumAgeRule";
  }

  @Override
  public boolean evaluate(LoanApplication context) {
    return context.age() >= minimumAge;
  }

  @Override
  public void execute(LoanApplication context) {
    LOGGER.info("Applicant age {} meets the minimum age of {}.", context.age(), minimumAge);
  }
}
```

The engine holds a defensively copied, immutable list of rules. It evaluates every rule in insertion order, executes the ones that pass, and reports the combined outcome. It never stops at the first failure, so a single run reports *all* the reasons an application was rejected.

```java
public class RuleEngine<T> {

  private final List<Rule<T>> rules;

  public RuleEngine(List<Rule<T>> rules) {
    this.rules = List.copyOf(rules);
  }

  public RuleEngineResult run(T context) {
    Objects.requireNonNull(context, "context must not be null");
    List<String> passed = new ArrayList<>();
    List<String> failed = new ArrayList<>();
    for (Rule<T> rule : rules) {
      if (rule.evaluate(context)) {
        rule.execute(context);
        passed.add(rule.name());
      } else {
        failed.add(rule.name());
      }
    }
    return new RuleEngineResult(failed.isEmpty(), passed, failed);
  }
}
```

The result is an immutable value object describing the run.

```java
public record RuleEngineResult(
    boolean approved, List<String> passedRules, List<String> failedRules) {

  public RuleEngineResult {
    passedRules = List.copyOf(passedRules);
    failedRules = List.copyOf(failedRules);
  }
}
```

Putting it together, the `App` builds an engine and runs two applications through it.

```java
var engine =
    new RuleEngine<>(
        List.of(
            new MinimumAgeRule(18),
            new MinimumIncomeRule(2000.0),
            new CreditScoreRule(650)));

engine.run(new LoanApplication(30, 3500.0, 15000.0, 720)); // approved
engine.run(new LoanApplication(17, 1500.0, 15000.0, 720)); // rejected: age and income
```

Program output:

```
Applicant age 30 meets the minimum age of 18.
Applicant income 3500.0 meets the minimum income of 2000.0.
Applicant credit score 720 meets the minimum score of 650.
Loan approved. Passed rules: [MinimumAgeRule, MinimumIncomeRule, CreditScoreRule]
Applicant credit score 720 meets the minimum score of 650.
Loan rejected. Failed rules: [MinimumAgeRule, MinimumIncomeRule]
```

### Behavioral decisions

The example commits to the following semantics, each covered by tests:

* `evaluate` returning `true` means the rule **passes** (its condition is satisfied); a passing rule then has its `execute` action run.
* The engine evaluates **every** rule; it does not stop after the first failure, so all rejection reasons are reported.
* Outcomes are reported as an immutable `RuleEngineResult` carrying the overall `approved` flag plus the names of the passed and failed rules.
* Rules execute in **insertion order**.
* A `null` context passed to `run` throws `NullPointerException`; a `null` rule collection or a `null` rule element is rejected on construction.
* An engine with **no rules** approves vacuously (nothing failed).

## Class diagram

See [rule-engine.urm.puml](./etc/rule-engine.urm.puml) for the PlantUML class diagram.

## When to Use the Rule Engine Pattern in Java

* Decision logic is a growing set of independent conditions that would otherwise become a large, hard-to-read nested `if/else`.
* Rules must be added, removed, or reordered without touching the code that coordinates them.
* You need to report *all* failing conditions, not just the first one.
* Business rules deserve to be unit tested in isolation.

## Real-World Applications of Rule Engine Pattern in Java

* Loan, insurance, and credit approval workflows.
* Validation frameworks such as Bean Validation, where each constraint is an independent rule.
* Fraud detection and risk scoring, where many independent checks contribute to one decision.
* Pricing, discount, and promotion eligibility engines.

## Benefits and Trade-offs of Rule Engine Pattern

Benefits

* Encapsulation: each rule owns one criterion.
* Extensibility: new rules are new classes; the engine is closed for modification.
* Testability: rules and the engine can be tested independently.
* Transparency: the result lists every passed and failed rule.

Trade-offs

* Debugging a decision means tracing several small objects instead of reading one block.
* Rule ordering and conflicts must be managed deliberately when rules are not independent.
* Over-abstracting trivial logic into a rule engine adds indirection that a simple `if` would not.

## Related Java Design Patterns

* [Specification](../specification): combines boolean criteria that an object must satisfy; a Rule Engine orchestrates and executes such criteria.
* [Chain of Responsibility](../chain-of-responsibility): passes a request along handlers; a Rule Engine instead runs every rule against one context.
* [Strategy](../strategy): each rule is effectively a pluggable strategy for one decision.
* [Command](../command): a rule's `execute` action resembles an encapsulated command.

## References and Credits

* [Patterns of Enterprise Application Architecture](https://www.amazon.com/gp/product/0321127420) (Martin Fowler)
* [Should I use a Rules Engine? (Martin Fowler)](https://martinfowler.com/bliki/RulesEngine.html)
