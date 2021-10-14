---
layout: pattern
title: Role Object
folder: role-object
permalink: /patterns/role-object/
categories: Structural
language: en
tags:
 - Extensibility
---

## Also known as
Post pattern, Extension Object pattern

## Intent
Adapt an object to different client’s needs through transparently attached role objects, each one representing a role
the object has to play in that client’s context. The object manages its role set dynamically. By representing roles as
individual objects, different contexts are kept separate and system configuration is simplified.

## Class diagram
![alt text](./etc/role-object.urm.png "Role Object pattern class diagram")

## Applicability
Use the Role Object pattern, if:

- You want to handle a key abstraction in different contexts and you do not want to put the resulting context specific interfaces into the same class interface.
- You want to handle the available roles dynamically so that they can be attached and removed on demand, that is at runtime, rather than fixing them statically at compile-time.
- You want to treat the extensions transparently and need to preserve the logical object identity of the resultingobject conglomerate.
- You want to keep role/client pairs independent from each other so that changes to a role do not affect clients that are not interested in that role.

## Credits

- [Hillside - Role object pattern](https://hillside.net/plop/plop97/Proceedings/riehle.pdf)
- [Role object](http://wiki.c2.com/?RoleObject)
- [Fowler - Dealing with roles](https://martinfowler.com/apsupp/roles.pdf)
