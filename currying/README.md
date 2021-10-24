---
layout: pattern title: Currying folder: currying permalink: /patterns/currying/ categories: Functional language: en
tags:

- Decoupling

---

## Intent

Currying pattern transforms an arbitrary arity into a sequence of unary functions.

## Explanation

Real-world example

> We have a Staff class for storing employees' information, e.g. firstName, lastName,
> gender, email, etc. We want to adapt currying pattern to simplify the parameters handling,
> we don't need to provide all parameters at the same time. We can provide them one by one
> when we have that data.

In plain words

> It helps you to avoid passing the same variable again and again.
> It helps to create a higher order function. It extremely helpful in event handling.
> Little snippets of code can be written and reused with ease.

Wikipedia says

> Currying provides a way for working with functions that take multiple arguments,
> and using them in frameworks where functions might take only one argument.
> For example, some analytical techniques can only be applied to functions with a
> single argument. Practical functions frequently take more arguments than this.
> Frege showed that it was sufficient to provide solutions for the single argument
> case, as it was possible to transform a function with multiple arguments into a
> chain of single-argument functions instead. This transformation is the process
> now known as currying.

**Programmatic Example**

First, we have the `Staff` class:

```java

@AllArgsConstructor
@Data
public class Staff {
    private String firstName;
    private String lastName;
    private Gender gender;
    private String email;
    private LocalDate dateOfBirth;
}
```

By using @AllArgsConstructor from Lombok library, it will generate a constructor with all attributes. All paramaters
need to provide at the same time.

Next, we will try to use Curry pattern to transforms an arbitrary arity into a sequence of unary functions.

```java
static Function<String,
        Function<String,
        Function<Gender,
        Function<String,
        Function<LocalDate, Staff>>>>>CREATOR=
        firstName->lastName
        ->gender->email
        ->dateOfBirth
        ->new Staff(firstName,lastName,gender,
        email,dateOfBirth);
```

With using this way, we can create Staff object by providing parameters one by one:

```java
Staff.CREATOR
        .apply(firstName)
        .apply(lastName)
        .apply(gender)
        .apply(email)
        .apply(dateOfBirth);
```

We can also use Functional Interface to implement currying function.

```java
static AddFirstName builder(){
        return firstName->lastName
        ->gender->email
        ->dateOfBirth
        ->new Staff(firstName,lastName,gender,email,dateOfBirth);
        }

interface AddFirstName {
    AddLastName withReturnFirstName(String firstName);
}

interface AddLastName {
    AddGender withReturnLastName(String lastName);
}

interface AddGender {
    AddEmail withReturnGender(Gender gender);
}

interface AddEmail {
    AddDateOfBirth withReturnEmail(String email);
}

interface AddDateOfBirth {
    Staff withReturnDateOfBirth(LocalDate dateOfBirth);
}
```

## Applicability

Use the currying pattern in any of the following situations

* when you want to break a function with many arguments into many functions with single argument

## Tutorials

* [Currying in Java](https://www.baeldung.com/java-currying)

## Credits

* [Baeldung](https://www.baeldung.com/)