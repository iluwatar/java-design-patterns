---
title: Step Builder
category: Creational
language: es
tag:
  - Instantiation
---

# Step Builder Pattern

## Explicación

El patrón Step Builder es un patrón de diseño de creación utilizado para construir un objeto complejo paso a paso.
Proporciona una interfaz fluida para crear un objeto con un gran número de configuraciones posibles, haciendo el código
más legible y reduciendo la necesidad de múltiples constructores o métodos setter.

## Propósito

Una extensión del patrón Builder que guía completamente al usuario a través de la creación del objeto sin posibilidades
de confusión.
La experiencia del usuario será mucho más mejorada por el hecho de que solo verá los métodos del siguiente paso
disponibles, NINGÚN método constructor hasta que sea el momento adecuado para construir el objeto.

## Ejemplo del mundo real

Imagine que está construyendo un objeto de configuración para una conexión a una base de datos. La conexión tiene varios
parámetros opcionales como host, puerto, nombre de usuario, contraseña y otros. Usando el patrón Step Builder, puedes
establecer estos parámetros de una forma limpia y legible:

```java
DatabaseConnection connection = new DatabaseConnection.Builder()
    .setHost("localhost")
    .setPort(3306)
    .setUsername("user")
    .setPassword("password")
    .setSSL(true)
    .build();
```

## En pocas palabras

El patrón Step Builder permite construir objetos complejos dividiendo el proceso de construcción en una serie de pasos.
Cada paso corresponde a la configuración de un atributo particular u opción de configuración del objeto. Esto resulta en
un código más legible y mantenible, especialmente cuando se trata de objetos que tienen numerosas opciones de
configuración.

## Wikipedia dice

Según Wikipedia, el patrón Step Builder es un patrón de diseño de creación en el que un objeto se construye paso a paso.
Implica una clase 'director' dedicada, que orquesta el proceso de construcción a través de una serie de clases '
constructoras', cada una responsable de un aspecto específico de la configuración del objeto. Este patrón es
particularmente útil cuando se trata de objetos que tienen un gran número de parámetros opcionales.

## Ejemplo programático

Suponiendo que tienes una clase `Producto` con varios atributos configurables, un Step Builder para ella podría tener
este aspecto:

```java
public class Product {
    private String name;
    private double price;
    private int quantity;

    // private constructor to force the use of the builder
    private Product(String name, double price, int quantity) {

        this.name = name;
        this.price = price;
        this.quantity = quantity;

    }

    public static class Builder {
        private String name;
        private double price;
        private int quantity;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setPrice(double price) {
            this.price = price;
            return this;
        }

        public Builder setQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Product build() {
            return new Product(name, price, quantity);
        }
    }
}

// Usage
Product product = new Product.Builder()
    .setName("Example Product")
    .setPrice(29.99)
    .setQuantity(100)
    .build();
```

Este ejemplo muestra cómo utilizar el patrón Constructor por pasos para crear un objeto `Producto` con un conjunto de
atributos personalizables. Cada método del constructor corresponde a un paso del proceso de construcción.

## Aplicabilidad

Utilice el patrón Step Builder cuando el algoritmo para crear un objeto complejo debe ser independiente de las partes
que componen el objeto y de cómo se ensamblan el proceso de construcción debe permitir diferentes representaciones para
el objeto que se construye cuando en el proceso de construcción el orden es importante.

## Otro ejemplo con diagrama de clases

![alt text](./etc/step-builder.png "Step Builder pattern")

## Créditos

* [Marco Castigliego - Step Builder](http://rdafbn.blogspot.co.uk/2012/07/step-builder-pattern_28.html)
