---
title: Feature Toggle
category: Behavioral
language: en
tag:
    - Decoupling
    - Extensibility
    - Feature management
    - Scalability
---

## Also known as

* Feature Flag
* Feature Switch

## Intent

A technique used in software development to control and manage the rollout of specific features or functionality in a program without changing the code. It can act as an on/off switch for features depending on the status or properties of other values in the program. This is similar to A/B testing, where features are rolled out based on properties such as location or device. Implementing this design pattern can increase code complexity, and it is important to remember to remove redundant code if this design pattern is being used to phase out a system or feature.

## Explanation

Real-world Example

> This design pattern works really well in any sort of development, in particular mobile development. Say you want to introduce a feature such as dark mode, but you want to ensure that the feature works correctly and don't want to roll out the feature to everyone immediately. You write in the code, and have it switched off as default. From here, it is easy to turn on the code for specific users based on selection criteria, or randomly. This will also allow the feature to be turned off easily without any drastic changes to the code, or any need for redeployment or updates.

In plain words

> Feature Toggle is a way to introduce new features gradually instead of deployment all at once.

Wikipedia says

> A feature toggle in software development provides an alternative to maintaining multiple feature branches in source code. A condition within the code enables or disables a feature during runtime. In agile settings the toggle is used in production, to switch on the feature on demand, for some or all the users.

## Programmatic Example

This example shows Java code that allows a feature to show when it is enabled by the developer, and when a user is a Premium member of the application. This is useful for subscription locked features.

```java
public class FeatureToggleExample {
    // Bool for feature enabled or disabled
    private static boolean isNewFeatureEnabled = false;

    public static void main(String[] args) {
        boolean userIsPremium = true; // Example: Check if the user is a premium user

        // Check if the new feature should be enabled for the user
        if (userIsPremium && isNewFeatureEnabled) {
            // User is premium and the new feature is enabled
            showNewFeature();
        }
    }

    private static void showNewFeature() {
        // If user is allowed to see locked feature, this is where the code would go
    }
}
```

The code shows how simple it is to implement this design pattern, and the criteria can be further refined or broadened should the developers choose to do so.

## Class diagram

![Feature Toggle](./etc/feature-toggle.png "Feature Toggle")

## Applicability

Use the Feature Toggle pattern when

* Conditional feature access to different users and groups.
* Rolling out a new feature incrementally.
* Switching between development and production environments.
* Quickly disable problematic features
* External management of feature deployment
* Ability to maintain multiple version releases of a feature
* 'Hidden' deployment, releasing a feature in code for designated testing but not publicly making it available

## Known Uses

* Web development platforms use feature toggles to gradually roll out new features to users to ensure stability.
* Enterprise applications use feature toggles to enable or disable features during runtime to cater to different market needs.

## Consequences

Benefits:

* Facilitates A/B testing and canary releases.
* Allows for quicker rollback and minimal risk deployments.
* Enables conditional feature execution without redeploying the application.

Trade-offs:

* Code complexity is increased.
* Testing of multiple states is harder and more time-consuming.
* Potential for technical debt if toggles remain in the code longer than necessary.
* Risk of toggle misconfiguration leading to unexpected behavior.

## Related Patterns

* [Strategy](https://java-design-patterns.com/patterns/strategy/): Both patterns allow changing the behavior of software at runtime. The Feature Toggle changes features dynamically, while the Strategy allows switching algorithms or strategies.
* [Observer](https://java-design-patterns.com/patterns/observer/): Useful for implementing feature toggles by notifying components of feature state changes, which allows dynamic feature modification without restarts.

## Credits

* [Feature Toggle - Martin Fowler](http://martinfowler.com/bliki/FeatureToggle.html)
* [Continuous Delivery: Reliable Software Releases through Build, Test, and Deployment Automation](https://amzn.to/4488ESM)
* [Release It! Design and Deploy Production-Ready Software](https://amzn.to/3UoeJY4)
