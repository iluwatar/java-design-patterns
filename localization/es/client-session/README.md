---
title: Client Session
category: Behavioral
language: es
tags:
    - Session management
    - Web development
---

## También conocido como

* User session

## Propósito

El patrón de diseño Client Session tiene como objetivo mantener el estado y los datos de un usuario a través de múltiples peticiones dentro de una aplicación web, asegurando una experiencia de usuario continua y personalizada.

## Explicación

Ejemplo real

> Quieres crear una aplicación de gestión de datos que permita a los usuarios enviar peticiones al servidor para modificar y realizar cambios en los datos almacenados en sus dispositivos. Estas peticiones son pequeñas y los datos son individuales para cada usuario, negando la necesidad de una implementación de base de datos a gran escala. Utilizando el patrón de sesión de cliente, se pueden gestionar múltiples peticiones simultáneas, equilibrando la carga de clientes entre diferentes servidores con facilidad debido a que los servidores permanecen sin estado. También se elimina la necesidad de almacenar identificadores de sesión en el lado del servidor debido a que los clientes proporcionan toda la información que un servidor necesita para realizar su proceso.

En pocas palabras

> En lugar de almacenar información sobre el cliente actual y la información a la que se está accediendo en el servidor, se mantiene sólo en el lado del cliente. El cliente tiene que enviar datos de sesión con cada solicitud al servidor y tiene que enviar un estado actualizado de vuelta al cliente, que se almacena en la máquina del cliente. El servidor no tiene que almacenar la información del cliente. ([ref](https://dzone.com/articles/practical-php-patterns/practical-php-patterns-client))

**Ejemplo programático**

Aquí está el código de ejemplo para describir el patrón cliente-sesión. En el siguiente código estamos creando primero una instancia del Servidor. Esta instancia del servidor se utilizará entonces para obtener objetos Session para dos clientes. Como puedes ver en el siguiente código, el objeto Session puede ser utilizado para almacenar cualquier información relevante que sea requerida por el servidor para procesar la petición del cliente. Estos objetos Session serán pasados con cada Request al servidor. La solicitud tendrá el objeto Session que almacena los detalles relevantes del cliente junto con los datos requeridos para procesar la solicitud. La información de sesión en cada solicitud ayuda al servidor a identificar al cliente y procesar la solicitud en consecuencia.

```java
public class App {

    public static void main(String[] args) {
        var server = new Server("localhost", 8080);
        var session1 = server.getSession("Session1");
        var session2 = server.getSession("Session2");
        var request1 = new Request("Data1", session1);
        var request2 = new Request("Data2", session2);
        server.process(request1);
        server.process(request2);
    }
}

@Data
@AllArgsConstructor
public class Session {

    /**
     * Session id.
     */
    private String id;

    /**
     * Client name.
     */
    private String clientName;

}

@Data
@AllArgsConstructor
public class Request {

    private String data;

    private Session session;

}
```

## Diagrama de arquitectura

![alt text](./etc/session_state_pattern.png "Session State Pattern")

## Aplicabilidad

Utilice el patrón de estado del cliente cuando:

* Aplicaciones web que requieran autenticación y autorización del usuario.
* Aplicaciones que necesiten realizar un seguimiento de las actividades y preferencias del usuario a lo largo de múltiples peticiones o visitas.
* Sistemas donde los recursos del servidor necesitan ser optimizados descargando la gestión del estado al lado del cliente.

## Usos conocidos

* Sitios web de comercio electrónico para rastrear el contenido de la cesta de la compra a lo largo de las sesiones.
* Plataformas en línea que ofrecen contenidos personalizados basados en las preferencias y el historial del usuario.
* Aplicaciones web que requieren el inicio de sesión del usuario para acceder a contenidos personalizados o seguros.

## Consecuencias

Beneficios:

* Mejora del rendimiento del servidor al reducir la necesidad de almacenar el estado del usuario en el servidor.
* Mejora de la experiencia del usuario a través de contenidos personalizados y navegación fluida a través de las diferentes partes de la aplicación.
* Flexibilidad en la gestión de sesiones a través de varios mecanismos de almacenamiento del lado del cliente (por ejemplo, cookies, Web Storage API).

Desventajas:

* Riesgos potenciales de seguridad si se almacena información sensible en las sesiones del cliente sin el cifrado y la validación adecuados.
* Dependencia de las capacidades y ajustes del cliente, como las políticas de cookies, que pueden variar según el navegador y la configuración del usuario.
* Mayor complejidad en la lógica de gestión de sesiones, especialmente en la gestión de la caducidad, renovación y sincronización de sesiones en varios dispositivos o pestañas.

## Patrones relacionados

* Sesión Servidor: A menudo se utiliza junto con el patrón Client Session para proporcionar un equilibrio entre la eficiencia del lado del cliente y el control del lado del servidor.
* [Singleton](https://java-design-patterns.com/patterns/singleton/): Asegurar una única instancia de la sesión de un usuario en toda la aplicación.
* [Estado](https://java-design-patterns.com/patterns/state/): Gestionar las transiciones de estado en una sesión, como los estados autenticado, invitado o caducado.

## Créditos

* [DZone - Practical PHP patterns](https://dzone.com/articles/practical-php-patterns/practical-php-patterns-client)
* [Client Session State Design Pattern - Ram N Java](https://www.youtube.com/watch?v=ycOSj9g41pc)
* [Professional Java for Web Applications](https://amzn.to/4aazY59)
* [Securing Web Applications with Spring Security](https://amzn.to/3PCCEA1)
