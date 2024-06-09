---
title: "Double Buffer Pattern in Java: Enhancing Animation and Graphics Performance"
shortTitle: Double Buffer
description: "Learn how the Double Buffer Pattern in Java optimizes performance and ensures smooth graphics rendering for applications. Explore practical examples and real-world use cases."
category: Behavioral
language: en
tag:
  - Buffering
  - Game programming
  - Optimization
  - Performance
---

## Also known as

* Buffer Switching
* Ping-Pong Buffer

## Intent of Double Buffer Design Pattern

The Double Buffer pattern in Java is designed to reduce rendering time and enhance performance in graphical or computational applications by utilizing two buffers. This pattern is crucial for smooth graphics rendering and is commonly used in game development and other real-time applications.

## Detailed Explanation of Double Buffer Pattern with Real-World Examples

Real-world example

> Imagine a busy restaurant kitchen where chefs are constantly preparing dishes, and waitstaff are constantly picking up ready dishes to serve to customers. To avoid confusion and delays, the restaurant uses a double buffer system. They have two counters: one for chefs to place newly prepared dishes and another for waitstaff to pick up the dishes. While the chefs are filling one counter with prepared dishes, the waitstaff are simultaneously clearing the other counter by picking up dishes to serve. Once the waitstaff have cleared all dishes from their counter, they switch to the counter where the chefs have placed the newly prepared dishes, and the chefs start filling the now-empty counter. This system ensures a smooth and continuous workflow without either party waiting idly, maximizing efficiency and minimizing downtime.

In plain words

> It ensures a state that is being rendered correctly while that state is modifying incrementally. It is widely used in computer graphics.

Wikipedia says

> In computer science, multiple buffering is the use of more than one buffer to hold a block of data, so that a "reader" will see a complete (though perhaps old) version of the data, rather than a partially updated version of the data being created by a "writer". It is very commonly used for computer display images.

## Programmatic Example of Double Buffer Pattern in Java

A typical example, and one that every game engine must address, is rendering. When the game draws the world the users see, it does so one piece at a time - the mountains in the distance, the rolling hills, the trees, each in its turn. If the user watched the view draw incrementally like that, the illusion of a coherent world would be shattered. The scene must update smoothly and quickly, displaying a series of complete frames, each appearing instantly. Double buffering solves the problem.

`Buffer` interface that assures basic functionalities of a buffer.

```java
public interface Buffer {

    void clear(int x, int y);

    void draw(int x, int y);

    void clearAll();

    Pixel[] getPixels();
}
```

One of the implementations of `Buffer` interface.

```java
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

We support black and white pixels.

```java
public enum Pixel {

    WHITE,
    BLACK
}
```

`Scene` represents the game scene where current buffer has already been rendered.

```java
@Slf4j
public class Scene {

    private final Buffer[] frameBuffers;

    private int current;

    private int next;

    public Scene() {
        frameBuffers = new FrameBuffer[2];
        frameBuffers[0] = new FrameBuffer();
        frameBuffers[1] = new FrameBuffer();
        current = 0;
        next = 1;
    }

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

Now, we can show the `App` class that drives the double buffering example.

```java
@Slf4j
public class App {

    public static void main(String[] args) {
        final var scene = new Scene();
        var drawPixels1 = List.of(
                new MutablePair<>(1, 1),
                new MutablePair<>(5, 6),
                new MutablePair<>(3, 2)
        );
        scene.draw(drawPixels1);
        var buffer1 = scene.getBuffer();
        printBlackPixelCoordinate(buffer1);

        var drawPixels2 = List.of(
                new MutablePair<>(3, 7),
                new MutablePair<>(6, 1)
        );
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
}
```

The console output:

```text
12:33:02.525 [main] INFO com.iluwatar.doublebuffer.Scene -- Start drawing next frame
12:33:02.529 [main] INFO com.iluwatar.doublebuffer.Scene -- Current buffer: 0 Next buffer: 1
12:33:02.529 [main] INFO com.iluwatar.doublebuffer.Scene -- Swap current and next buffer
12:33:02.529 [main] INFO com.iluwatar.doublebuffer.Scene -- Finish swapping
12:33:02.529 [main] INFO com.iluwatar.doublebuffer.Scene -- Current buffer: 1 Next buffer: 0
12:33:02.530 [main] INFO com.iluwatar.doublebuffer.Scene -- Get current buffer: 1
12:33:02.530 [main] INFO com.iluwatar.doublebuffer.App -- Black Pixels:  (1, 1) (3, 2) (5, 6)
12:33:02.530 [main] INFO com.iluwatar.doublebuffer.Scene -- Start drawing next frame
12:33:02.530 [main] INFO com.iluwatar.doublebuffer.Scene -- Current buffer: 1 Next buffer: 0
12:33:02.530 [main] INFO com.iluwatar.doublebuffer.Scene -- Swap current and next buffer
12:33:02.530 [main] INFO com.iluwatar.doublebuffer.Scene -- Finish swapping
12:33:02.530 [main] INFO com.iluwatar.doublebuffer.Scene -- Current buffer: 0 Next buffer: 1
12:33:02.530 [main] INFO com.iluwatar.doublebuffer.Scene -- Get current buffer: 0
12:33:02.530 [main] INFO com.iluwatar.doublebuffer.App -- Black Pixels:  (6, 1) (3, 7)
```

## When to Use the Double Buffer Pattern in Java

* Real-time Applications: Ideal for video games, simulations, and GUI applications where frequent and smooth display updates are essential.
* High Computational Tasks: Suitable for applications that require intensive data preparation, enabling parallel processing and display.
* Minimizing Lag: Effective in reducing lag or stutter in data or graphics display.

## Real-World Applications of Double Buffer Pattern in Java

* Graphics Rendering Engines: Widely used in 2D and 3D rendering engines to ensure fluid animations and transitions.
* GUI Frameworks: Enhances the responsiveness and smoothness of user interfaces.
* Simulation and Modeling: Ensures real-time updates in simulations without interrupting ongoing processes.
* Video Playback Software: Provides seamless video playback by preloading the next frame during the display of the current one.

## Benefits and Trade-offs of Double Buffer Pattern

Benefits:

* Smooth User Experience: Pre-renders frames to deliver smooth animations and transitions.
* Performance Optimization: Allows background rendering, optimizing overall application performance.
* Minimized Flickering: Reduces flickering and visual artifacts in graphical applications.

Trade-offs:

* Memory Overhead: Requires additional memory for the secondary buffer, potentially increasing memory usage.
* Implementation Complexity: Adds complexity to the architecture, necessitating careful buffer management.
* Latency: May introduce slight delays as data must be fully rendered in the back buffer before display.

## Related Java Design Patterns

* Triple Buffering: An extension of the Double Buffer pattern, where three buffers are used to further optimize rendering and reduce latency.
* [Producer-Consumer](https://java-design-patterns.com/patterns/producer-consumer/): The Double Buffer pattern can be seen as a variant of the Producer-Consumer pattern, with one buffer being "produced" while the other is "consumed".
* [Strategy](https://java-design-patterns.com/patterns/strategy/): Often used in conjunction with the Strategy pattern to dynamically choose the buffering strategy based on runtime conditions.

## References and Credits

* [Game Programming Patterns](https://amzn.to/4ayDNkS)
* [Real-Time Design Patterns: Robust Scalable Architecture for Real-Time Systems](https://amzn.to/3xFfNxA)
* [Double Buffer (Game Programming Patterns)](https://gameprogrammingpatterns.com/double-buffer.html)
