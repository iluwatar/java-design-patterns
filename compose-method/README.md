---
layout: pattern
title: Compose-method
folder: compose-method
permalink: /patterns/compose-method/
categories: Structural
tags:
 - Gang of Four
---

## Intent

Transform the logic into a small number of intention-revealing steps at the same level of detail.

## Explanation

Real world example

> to make the method easy to read.

In plain words

> Composed Method is a small, simple method that you can understand in seconds.

Wikipedia says

> In software engineering, composed method is to divide your program into methods that perform one identifiable task. Keep all of the operations in a method at the same level of abstraction. This will naturally result in programs with many small methods, each a few lines long.

**Programmatic Example**

Refactoring to Patterns: Simplification | Compose Method | InformIT

The original method is relative hard to understand the meaning. 
```java
public class List{
    public void  add(Object element){
        if(!readOnly){
            int newSize = size + 1;
            if (newSize > elements.length) {
                Object[] newElements =
                        new Object[elements.length + 10];
                for (int i = 0; i < size; i++)
                    newElements[i] = elements[i];
                elements = newElements;
            }
            elements[size++] = element;
        }
    }
}
```

After use a guard clause, we can make an early exit from the method.

```java
public class List{
    public void add(Object element) {
        if (readOnly)
            return;
        int newSize = size + 1;
        if (newSize > elements.length) {
            Object[] newElements =
                    new Object[elements.length + 10];
            for (int i = 0; i < size; i++)
                newElements[i] = elements[i];
            elements = newElements;
        }
        elements[size++] = element;
    }
}
```

change the constant to be more communicative.

```java
public class List{
    private final static int GROWTH_INCREMENT = 10;
    
    public void add(Object element) {
        if (readOnly)
            return;
        int newSize = size + 1;
        if (newSize > elements.length) {
            Object[] newElements =
                    new Object[elements.length + GROWTH_INCREMENT];
            for (int i = 0; i < size; i++)
                newElements[i] = elements[i];
            elements = newElements;
        }
        elements[size++] = element;
    }
}
```

Finally, extract each part of the code as the methods.

```java
public class List{
    private final static int GROWTH_INCREMENT = 10;

    public void add(Object element) {
        if (readOnly)
            return;
        if (atCapacity())
            grow();
        addElement(element);
    }

    private boolean atCapacity() {
        return (size + 1) > elements.length;
    }

    private void grow() {
        Object[] newElements =
                new Object[elements.length + GROWTH_INCREMENT];
        for (int i = 0; i < size; i++)
            newElements[i] = elements[i];
        elements = newElements;
    }

    private void addElement(Object element) {
        elements[size++] = element;
    }
}
```

## Applicability

Use the Compose method pattern when

* You want to make the content of the method easy to read.
* You want to set each method with one function.

## Real world examples

* [java.awt.Container](http://docs.oracle.com/javase/8/docs/api/java/awt/Container.html) and [java.awt.Component](http://docs.oracle.com/javase/8/docs/api/java/awt/Component.html)

## Credits

* [Refactoring to Patterns: Simplification](https://www.informit.com/articles/article.aspx?p=1398607)