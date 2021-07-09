---
layout: pattern
title: Role Object
folder: role-object
permalink: /patterns/role-object/zh
categories: Structural
language: zh
tags:
 - Extensibility
---

## 又称为
Post模式，扩展对象模式

## 意图
通过透明附加的角色对象使对象适应不同的客户端需求，每个角色代表一个角色
对象必须在该客户机的上下文中运行。对象动态管理其角色集。通过将角色表示为
单独的对象、不同的上下文保持独立，并且简化了系统配置。

## 类图
![alt text](./etc/role-object.urm.png "Role Object pattern class diagram")

## 适用性
使用角色对象模式，如果:

- 你想要在不同的上下文中处理一个关键抽象，你不想把产生的上下文特定的接口放到同一个类接口中。
- 您希望动态地处理可用的角色，以便它们可以在运行时随需附加和删除，而不是在编译时静态地修复它们。
— 您希望透明地对待扩展，并需要保留结果对象组合的逻辑对象标识。
— 角色/客户端对应该是相互独立的，这样角色的更改不会影响到不感兴趣的客户端。
## 鸣谢

- [Hillside - Role object pattern](https://hillside.net/plop/plop97/Proceedings/riehle.pdf)
- [Role object](http://wiki.c2.com/?RoleObject)
- [Fowler - Dealing with roles](https://martinfowler.com/apsupp/roles.pdf)
