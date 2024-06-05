---
title: Role Object
category: Structural
language: es
tag:
 - Extensibility
---

## También conocido como
Patrón Post, Patrón Extension Object

## Propósito
Adaptar un objeto a las necesidades de diferentes clientes mediante objetos de rol adjuntos de forma transparente, cada uno de los cuales representa un papel
que el objeto debe desempeñar en el contexto de ese cliente. El objeto gestiona su conjunto de roles de forma dinámica. Al representar los roles como
objetos individuales, los distintos contextos se mantienen separados y se simplifica la configuración del sistema.

## Diagrama de clases
![alt text](./etc/role-object.urm.png "Role Object pattern class diagram")

## Aplicabilidad
Utiliza el patrón Objeto Rol, si:

- Quieres manejar una abstracción clave en diferentes contextos y no quieres poner las interfaces específicas de contexto resultantes en la misma interfaz de clase.
- Quieres manejar los roles disponibles dinámicamente para que puedan ser adjuntados y removidos bajo demanda, es decir en tiempo de ejecución, en lugar de fijarlos estáticamente en tiempo de compilación.
- Quiere tratar las extensiones de forma transparente y necesita preservar la identidad lógica del objeto del conglomerado de objetos resultante.
- Desea mantener los pares rol/cliente independientes entre sí, de modo que los cambios en un rol no afecten a los clientes que no estén interesados en ese rol.

## Créditos

- [Hillside - Role object pattern](https://hillside.net/plop/plop97/Proceedings/riehle.pdf)
- [Role object](http://wiki.c2.com/?RoleObject)
- [Fowler - Dealing with roles](https://martinfowler.com/apsupp/roles.pdf)
