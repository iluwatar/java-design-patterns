---
layout: pattern
title: Presentation
folder: presentation
permalink: /patterns/presentation/
categories: Behavioral
tags:
 - Decoupling
---
## Also known as
Application Model

## Intent
Presentation Model pulls the state and behavior of the view out into a model class that is part of the presentation. The Presentation Model coordinates with the domain layer and provides an interface to the view that minimizes decision making in the view. The view either stores all its state in the Presentation Model or synchronizes its state with Presentation Model frequently

## Explanation

Example

> When we need to write a program with GUI,  there is no need for us to put all presentation behavior in the view class. Because it will test become harder. So we can use Presentation Model Pattern to separate the behavior and view. The view only need to load the data and states from other class and show these data on the screen according to the states.


## Class diagram
![](./etc/presentation.urm.png "presentation model")

## Applicability
Use the Presentation Model Pattern when

* Testing a presentation through a GUI window is often awkward, and in some cases impossible.
* Do not determine which GUI will be used.

## Related patterns

- [Supervising Controller](https://martinfowler.com/eaaDev/SupervisingPresenter.html) 
- [Passive View](https://martinfowler.com/eaaDev/PassiveScreen.html)

## Credits

* [Presentation Model Patterns](https://martinfowler.com/eaaDev/PresentationModel.html)

