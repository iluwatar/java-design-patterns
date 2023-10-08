---
title: Feature Toggle
category: Behavioral
language: en
tag:
 - Extensibility
---

## Also known as
Feature Flag

## Intent
A technique used in software development to control and manage the rollout of specific features or functionality in a 
program without changing the code. It can act as an on/off switch for features depending on the status or properties of
other values in the program. This is similar to A/B testing, where features are rolled out based on properties such as
location or device. Implementing this design pattern can increase code complexity, and it is important to remember to
remove redundant code if this design pattern is being used to phase out a system or feature.

## Explanation
Real-world Example
> This design pattern works really well in any sort of development, in particular mobile development. Say you want to
> introduce a feature such as dark mode, but you want to ensure that the feature works correctly and don't want to roll
> out the feature to everyone immediately. You write in the code, and have it switched off as default. From here, it is
> easy to turn on the code for specific users based on selection criteria, or randomly. This will also allow the feature
> to be turned off easily without any drastic changes to the code, or any need for redeployment or updates.

In plain words
> Feature Toggle is a way to introduce new features gradually instead of deployment all at once.

Wikipedia says
> A feature toggle in software development provides an alternative to maintaining multiple feature branches in source 
> code. A condition within the code enables or disables a feature during runtime. In agile settings the toggle is 
> used in production, to switch on the feature on demand, for some or all the users.

## Programmatic Example
This example shows Java code that allows a feature to show when it is enabled by the developer, and when a user is a 
Premium member of the application. This is useful for subscription locked features.
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
The code shows how simple it is to implement this design pattern, and the criteria can be further refined or broadened
should the developers choose to do so.

## Class diagram
![alt text](./etc/feature-toggle.png "Feature Toggle")

## Applicability
Use the Feature Toggle pattern when

* Giving different features to different users.
* Rolling out a new feature incrementally.
* Switching between development and production environments.
* Quickly disable problematic features
* External management of feature deployment
* Ability to maintain multiple version releases of a feature
* 'Hidden' deployment, releasing a feature in code for designated testing but not publicly making it available

## Consequences
Consequences involved with using the Feature Toggle pattern

* Code complexity is increased
* Testing of multiple states is harder and more time-consuming
* Confusion between friends on why features are missing
* Keeping documentation up to date with all features can be difficult 

## Credits

* [Martin Fowler 29 October 2010 (2010-10-29).](http://martinfowler.com/bliki/FeatureToggle.html)
* [Feature Toggle - Java Design Patterns](https://java-design-patterns.com/patterns/feature-toggle/)
