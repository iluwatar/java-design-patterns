---
title: Separated Interface
shortTitle: Separated Interface
category: Structural
language: es
tag:
 - Decoupling
---


## Propósito

Separe la definición de la interfaz y su implementación en paquetes diferentes. Esto permite al cliente desconozca por completo la implementación.

## Explicación

Ejemplo del mundo real

> Se puede crear un generador de facturas con capacidad para utilizar diferentes calculadoras de impuestos que se pueden añadir en la factura en función del tipo de compra, región, etc.

En pocas palabras

> El patrón de interfaz separada anima a mantener las implementaciones de una interfaz desacopladas del cliente y su definición, para que el cliente no dependa de la implementación.

Un código cliente puede abstraer algunas funcionalidades específicas a una interfaz, y definir la definición de
la interfaz como una SPI ([Service Programming Interface](https://en.wikipedia.org/wiki/Service_provider_interface)
es una API pensada y abierta para ser implementada o ampliada por terceros). Otro paquete puede
implementar esta definición de interfaz con una lógica concreta, que se inyectará en el código del cliente en tiempo de ejecución (con un tercero).
cliente en tiempo de ejecución (con una tercera clase, inyectando la implementación en el cliente) o en tiempo de compilación
(utilizando el patrón Plugin con algún archivo configurable).

**Ejemplo programático**

**Cliente**

La clase `InvoiceGenerator` acepta el coste del producto y calcula el importe total a pagar, impuestos incluidos.
total a pagar, impuestos incluidos.

```java
public class InvoiceGenerator {

  private final TaxCalculator taxCalculator;

  private final double amount;

  public InvoiceGenerator(double amount, TaxCalculator taxCalculator) {
    this.amount = amount;
    this.taxCalculator = taxCalculator;
  }

  public double getAmountWithTax() {
    return amount + taxCalculator.calculate(amount);
  }

}
```

La lógica de cálculo de impuestos se delega en la interfaz `TaxCalculator`.

```java
public interface TaxCalculator {

  double calculate(double amount);

}
```

**Paquete de aplicación**

En otro paquete (que el cliente desconoce por completo) existen múltiples implementaciones
de la interfaz `TaxCalculator`. Una de ellas es `ForeignTaxCalculator`, que aplica un impuesto del 60
para los productos internacionales.

```java
public class ForeignTaxCalculator implements TaxCalculator {

  public static final double TAX_PERCENTAGE = 60;

  @Override
  public double calculate(double amount) {
    return amount * TAX_PERCENTAGE / 100.0;
  }

}
```

Otra es `DomesticTaxCalculator`, que grava con un 20% los productos internacionales.

```java
public class DomesticTaxCalculator implements TaxCalculator {

  public static final double TAX_PERCENTAGE = 20;

  @Override
  public double calculate(double amount) {
    return amount * TAX_PERCENTAGE / 100.0;
  }

}
```

Estas dos implementaciones son instanciadas e inyectadas en la clase cliente por la clase `App.java`.
en la clase cliente.

```java
    var internationalProductInvoice = new InvoiceGenerator(PRODUCT_COST, new ForeignTaxCalculator());

    LOGGER.info("Foreign Tax applied: {}", "" + internationalProductInvoice.getAmountWithTax());

    var domesticProductInvoice = new InvoiceGenerator(PRODUCT_COST, new DomesticTaxCalculator());

    LOGGER.info("Domestic Tax applied: {}", "" + domesticProductInvoice.getAmountWithTax());
```

## Diagrama de clases

![alt text](./etc/class_diagram.png "Separated Interface")

## Aplicabilidad

Utilice el patrón de interfaz separada cuando

* Estás desarrollando un paquete de framework, y tu framework necesita llamar a algún código de aplicación a través de interfaces.
* Tienes paquetes separados que implementan las funcionalidades que pueden ser conectadas a tu código cliente en tiempo de ejecución o de compilación.
* Su código reside en una capa a la que no se le permite llamar a la capa de implementación de la interfaz por norma. Por ejemplo, una capa de dominio necesita llamar a un mapeador de datos.

## Tutoriales

* [Separated Interface Tutorial](https://www.youtube.com/watch?v=d3k-hOA7k2Y)

## Créditos

* [Martin Fowler](https://www.martinfowler.com/eaaCatalog/separatedInterface.html)
* [Patterns of Enterprise Application Architecture](https://www.amazon.com/gp/product/0321127420/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=0321127420&linkId=e08dfb7f2cf6153542ef1b5a00b10abc)
