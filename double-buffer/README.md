---
title: Double Buffer 
category: Behavioral
language: en
tag:  
 - Performance
 - Game programming  
---
  
## Intent  
Double buffering is a term used to describe a device that has two buffers. The usage of multiple 
buffers increases the overall throughput of a device and helps prevents bottlenecks. This example 
shows using double buffer pattern on graphics. It is used to show one image or frame while a separate 
frame is being buffered to be shown next. This method makes animations and games look more realistic 
than the same done in a single buffer mode.   

## Explanation

Real world example
> A typical example, and one that every game engine must address, is rendering. When the game draws 
> the world the users see, it does so one piece at a time -- the mountains in the distance, 
> the rolling hills, the trees, each in its turn. If the user watched the view draw incrementally 
> like that, the illusion of a coherent world would be shattered. The scene must update smoothly 
> and quickly, displaying a series of complete frames, each appearing instantly. Double buffering solves
> the problem.

In plain words
> It ensures a state that is being rendered correctly while that state is modifying incrementally. It is
> widely used in computer graphics.

Wikipedia says
> In computer science, multiple buffering is the use of more than one buffer to hold a block of data, 
> so that a "reader" will see a complete (though perhaps old) version of the data, rather than a 
> partially updated version of the data being created by a "writer". It is very commonly used for 
> computer display images.

**Programmatic Example**

Buffer interface that assures basic functionalities of a buffer.

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

One of the implementation of Buffer interface.
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

  WHITE,
  BLACK;
}
```
Scene represents the game scene where current buffer has already been rendered.
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
```

The console output
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
