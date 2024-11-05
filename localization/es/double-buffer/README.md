---
title: Double Buffer
shortTitle: Double Buffer
category: Behavioral
language: es
tag:
    - Performance
    - Game programming  
---

## Propósito

Doble búfer es un término utilizado para describir un dispositivo que tiene dos búferes. El uso de varios búferes aumenta el rendimiento global de un dispositivo y ayuda a evitar cuellos de botella. Este ejemplo muestra el uso de doble búfer en gráficos. Se utiliza para mostrar una imagen o un fotograma mientras se almacena en el búfer otro fotograma que se mostrará a continuación. Este método hace que las animaciones y los juegos parezcan más realistas que los realizados en modo de búfer único.

## Explicación

Ejemplo del mundo real
> Un ejemplo típico, y que todo motor de juego debe abordar, es el renderizado. Cuando el juego dibuja el mundo que ven los usuarios, lo hace pieza a pieza: las montañas a lo lejos, las colinas ondulantes, los árboles, cada uno a su vez. Si el usuario viera cómo se dibuja la vista de forma incremental, se rompería la ilusión de un mundo coherente. La escena debe actualizarse con fluidez y rapidez, mostrando una serie de fotogramas completos, cada uno de los cuales aparece al instante. La doble memoria intermedia resuelve el problema.

En pocas palabras
> Garantiza un estado que se renderiza correctamente mientras ese estado se modifica de forma incremental. Se utiliza mucho en gráficos por ordenador.

Wikipedia dice
> En informática, el almacenamiento en búfer múltiple es el uso de más de un búfer para contener un bloque de datos, de modo que un "lector" vea una versión completa (aunque quizás antigua) de los datos, en lugar de una versión parcialmente actualizada de los datos que está creando un "escritor". Se utiliza mucho en las imágenes de ordenador.

**Ejemplo programático**

Interfaz `Buffer` que asegura las funcionalidades básicas de un buffer.

```java
/**
 * Buffer interface.
 */
public interface Buffer {

    /**
     * Clear the pixel in (x, y).
     *
     * @param x X coordinate
     * @param y Y coordinate
     */
    void clear(int x, int y);

    /**
     * Draw the pixel in (x, y).
     *
     * @param x X coordinate
     * @param y Y coordinate
     */
    void draw(int x, int y);

    /**
     * Clear all the pixels.
     */
    void clearAll();

    /**
     * Get all the pixels.
     *
     * @return pixel list
     */
    Pixel[] getPixels();

}
```

Una de las implementaciones de la interfaz `Buffer`.

```java
/**
 * FrameBuffer implementation class.
 */
public class FrameBuffer implements Buffer {

    public static final int WIDTH = 10;
    public static final int HEIGHT = 8;

    private final Pixel[] pixels = new Pixel[WIDTH * HEIGHT];

    public FrameBuffer() {
        clearAll();
    }

    @Override
    public void clear(int x, int y) {
        pixels[getIndex(x, y)] = Pixel.WHITE;
    }

    @Override
    public void draw(int x, int y) {
        pixels[getIndex(x, y)] = Pixel.BLACK;
    }

    @Override
    public void clearAll() {
        Arrays.fill(pixels, Pixel.WHITE);
    }

    @Override
    public Pixel[] getPixels() {
        return pixels;
    }

    private int getIndex(int x, int y) {
        return x + WIDTH * y;
    }
}
```

```java
/**
 * Pixel enum. Each pixel can be white (not drawn) or black (drawn).
 */
public enum Pixel {

    WHITE, BLACK;
}
```

`Scene` representa la escena del juego en la que ya se ha renderizado el búfer actual.

```java
/**
 * Scene class. Render the output frame.
 */
@Slf4j
public class Scene {

    private final Buffer[] frameBuffers;

    private int current;

    private int next;

    /**
     * Constructor of Scene.
     */
    public Scene() {
        frameBuffers = new FrameBuffer[2];
        frameBuffers[0] = new FrameBuffer();
        frameBuffers[1] = new FrameBuffer();
        current = 0;
        next = 1;
    }

    /**
     * Draw the next frame.
     *
     * @param coordinateList list of pixels of which the color should be black
     */
    public void draw(List<? extends Pair<Integer, Integer>> coordinateList) {
        LOGGER.info("Start drawing next frame");
        LOGGER.info("Current buffer: " + current + " Next buffer: " + next);
        frameBuffers[next].clearAll();
        coordinateList.forEach(coordinate -> {
            var x = coordinate.getKey();
            var y = coordinate.getValue();
            frameBuffers[next].draw(x, y);
        });
        LOGGER.info("Swap current and next buffer");
        swap();
        LOGGER.info("Finish swapping");
        LOGGER.info("Current buffer: " + current + " Next buffer: " + next);
    }

    public Buffer getBuffer() {
        LOGGER.info("Get current buffer: " + current);
        return frameBuffers[current];
    }

    private void swap() {
        current = current ^ next;
        next = current ^ next;
        current = current ^ next;
    }

}
```

```java
public static void main(String[] args) {
    final var scene = new Scene();
    var drawPixels1 = List.of(new MutablePair<>(1, 1), new MutablePair<>(5, 6), new MutablePair<>(3, 2));
    scene.draw(drawPixels1);
    var buffer1 = scene.getBuffer();
    printBlackPixelCoordinate(buffer1);

    var drawPixels2 = List.of(new MutablePair<>(3, 7), new MutablePair<>(6, 1));
    scene.draw(drawPixels2);
    var buffer2 = scene.getBuffer();
    printBlackPixelCoordinate(buffer2);
}

private static void printBlackPixelCoordinate(Buffer buffer) {
    StringBuilder log = new StringBuilder("Black Pixels: ");
    var pixels = buffer.getPixels();
    for (var i = 0; i < pixels.length; ++i) {
        if (pixels[i] == Pixel.BLACK) {
            var y = i / FrameBuffer.WIDTH;
            var x = i % FrameBuffer.WIDTH;
            log.append(" (").append(x).append(", ").append(y).append(")");
        }
    }
    LOGGER.info(log.toString());
}
```

La salida de la consola

```text
[main] INFO com.iluwatar.doublebuffer.Scene - Start drawing next frame
[main] INFO com.iluwatar.doublebuffer.Scene - Current buffer: 0 Next buffer: 1
[main] INFO com.iluwatar.doublebuffer.Scene - Swap current and next buffer
[main] INFO com.iluwatar.doublebuffer.Scene - Finish swapping
[main] INFO com.iluwatar.doublebuffer.Scene - Current buffer: 1 Next buffer: 0
[main] INFO com.iluwatar.doublebuffer.Scene - Get current buffer: 1
[main] INFO com.iluwatar.doublebuffer.App - Black Pixels:  (1, 1) (3, 2) (5, 6)
[main] INFO com.iluwatar.doublebuffer.Scene - Start drawing next frame
[main] INFO com.iluwatar.doublebuffer.Scene - Current buffer: 1 Next buffer: 0
[main] INFO com.iluwatar.doublebuffer.Scene - Swap current and next buffer
[main] INFO com.iluwatar.doublebuffer.Scene - Finish swapping
[main] INFO com.iluwatar.doublebuffer.Scene - Current buffer: 0 Next buffer: 1
[main] INFO com.iluwatar.doublebuffer.Scene - Get current buffer: 0
[main] INFO com.iluwatar.doublebuffer.App - Black Pixels:  (6, 1) (3, 7)
```

## Diagrama de clases

![alt text](./etc/double-buffer.urm.png "Double Buffer pattern class diagram")

## Aplicabilidad

Este patrón es uno de esos que sabrás cuándo lo necesitas. Si tienes un sistema que carece de doble búfer, probablemente tendrá un aspecto visiblemente incorrecto (tearing, etc.) o se comportará de forma incorrecta. Pero decir "lo sabrás cuando lo necesites" no da mucho de sí. Más concretamente, este patrón es apropiado cuando todo esto es cierto:

- Tenemos algún estado que está siendo modificado incrementalmente.
- Ese mismo estado puede ser accedido en medio de la modificación.
- Queremos evitar que el código que está accediendo al estado vea el trabajo en curso.
- Queremos poder leer el estado y no queremos tener que esperar mientras se escribe.

## Créditos

* [Game Programming Patterns - Double Buffer](http://gameprogrammingpatterns.com/double-buffer.html)
