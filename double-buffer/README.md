---  
layout: pattern  
title: Double Buffer 
folder:  double-buffer  
permalink: /patterns/double-buffer/  
categories: Behavioral
language: en
tags:  
 - Performance
 - Game programming  
---  
  
## Intent  
Double buffering is a term used to describe a device that has two buffers. The usage of multiple buffers increases the overall throughput of a device and helps prevents bottlenecks. This example shows using double buffer pattern on graphics. It is used to show one image or frame while a separate frame is being buffered to be shown next. This method makes animations and games look more realistic than the same done in a single buffer mode.   

## Class diagram
![alt text](./etc/double-buffer.urm.png "Double Buffer pattern class diagram")

## Applicability  
This pattern is one of those ones where you’ll know when you need it. If you have a system that lacks double buffering, it will probably look visibly wrong (tearing, etc.) or will behave incorrectly. But saying, “you’ll know when you need it” doesn’t give you much to go on. More specifically, this pattern is appropriate when all of these are true:

- We have some state that is being modified incrementally.
- That same state may be accessed in the middle of modification.
- We want to prevent the code that’s accessing the state from seeing the work in progress.
- We want to be able to read the state and we don’t want to have to wait while it’s being written. 

## Credits  
  
* [Game Programming Patterns - Double Buffer](http://gameprogrammingpatterns.com/double-buffer.html)
