---
title: Mute Idiom
category: Idiom
language: en
tag:
- Decoupling
---


## Intent
Provide a template to suppress any exceptions that either are declared but cannot occur or should only be logged;
while executing some business logic. The template removes the need to write repeated `try-catch` blocks.


## Explanation
Real World Example
> The vending machine contained in your office displays a warning when making a transaction. The issue occurs when the
> customer decides to pay with physical money that is not recognized by the system. However, you and everyone in the office
> only pays with the company credit card and will never encounter this issue.

In plain words
> The Mute Idiom design pattern is used to reduce the requirement of catching exceptions when they cannot be thrown or
> should be ignored when thrown. This applies in cases such as API functions, where the underlying code cannot be changed
> to include individual use cases.    

Programmatic Example

Converting the real-world example into a programmatic representation, we represent an API function as the 
office Vending machine

```java

public class VendingMachine {
   public void purchaseItem(int itemID, PaymentMethod paymentMethod) throws Exception {
       if (paymentMethod == PaymentMethod.Cash) {
           throw new Exception();
       }
       else {
           System.out.println("Here is your item");
       }
   }
}
```

```java
public enum PaymentMethod {
   Card,Cash
}
```

We then run our office's daily routine, which involves purchasing items 
from the vending machine with the company card, using the mute pattern to ignore the exceptions that can't be thrown
```java
package com.iluwatar.mute;

public class Office {
    private PaymentMethod companyCard = PaymentMethod.Card;
    private VendingMachine officeVendingMachine = new VendingMachine();

    public static void main(String[] args) {
        Office office = new Office();
        office.dailyRoutine();

    }
    public void dailyRoutine() {
        Mute.mute(() -> {
            officeVendingMachine.purchaseItem(1,companyCard);
            officeVendingMachine.purchaseItem(1,companyCard);
            officeVendingMachine.purchaseItem(1,companyCard);
        });
    }
}

```


## Class diagram
![alt text](./etc/mute-idiom.png "Mute Idiom")


## Applicability
Use this idiom when
* an API declares some exception but can never throw that exception eg. ByteArrayOutputStream bulk write method.
* you need to suppress some exception just by logging it, such as closing a resource.




## Credits


* [JOOQ: Mute Design Pattern](http://blog.jooq.org/2016/02/18/the-mute-design-pattern/)



