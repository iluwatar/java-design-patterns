# Immutable Object Pattern

## Intent
Ensure that an object's state cannot be modified 
after it is created.

## Explanation
An immutable object is one whose state cannot be 
changed once created. All fields are final and set 
only in the constructor.

## When to use
- When shared objects must not be changed
- For thread-safe programming
- When you need predictable behavior

## Example
HeroStats in a game — once created, stats remain fixed.