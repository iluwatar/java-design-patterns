---  
title: Subclass Sandbox 
category: Behavioral
language: en
tag:  
 - Game programming
---  

## Intent  
The subclass sandbox pattern describes a basic idea, while not having a lot of detailed mechanics. You will need the pattern when you have several similar subclasses. If you have to make a tiny change, then change the base class, while all subclasses shouldn't have to be touched. So the base class has to be able to provide all of the operations a derived class needs to perform.
  
## Explanation
Real world example 
> Consider we want to create some superpower in the game, and they need to move accompanied by a sound effect and spawn particles. Create many classes that contain similar methods or need a base class to derivate them? The subclass-sandbox pattern allows you to deal with this problem in the second way.

In plain words
> The subclass-sandbox is about moving the overlap methods in the subclasses to a base class which reduces the redundant rate in the classes.

Wikipedia says
> A base class defines an abstract sandbox method and several provided operations. Marking them protected makes it clear that they are for use by derived classes. Each derived sandboxed subclass implements the sandbox method using the provided operations.
>
**Programmatic Example**  
We start with the base class `Superpower`. It contains an abstract sandbox method `active()` and some provided operations.
```
public abstract class Superpower {

  protected Logger logger;

  protected abstract void activate();

  protected void move(double x, double y, double z) {
    logger.info("Move to ( " + x + ", " + y + ", " + z + " )");
  }

  protected void playSound(String soundName, int volume) {
    logger.info("Play " + soundName + " with volume " + volume);
  }

  protected void spawnParticles(String particleType, int count) {
    logger.info("Spawn " + count + " particle with type " + particleType);
  }
}
```
Next we are able to create derived sandboxed subclass that implements the sandbox method using the provided operations. Here is the first power:
```
public class SkyLaunch extends Superpower {

  public SkyLaunch() {
    super();
    logger = LoggerFactory.getLogger(SkyLaunch.class);
  }

  @Override
  protected void activate() {
    move(0, 0, 20);
    playSound("SKYLAUNCH_SOUND", 1);
    spawnParticles("SKYLAUNCH_PARTICLE", 100);
  }
}
```
Here is the second power.
```
public class GroundDive extends Superpower {

  public GroundDive() {
    super();
    logger = LoggerFactory.getLogger(GroundDive.class);
  }

  @Override
  protected void activate() {
    move(0, 0, -20);
    playSound("GROUNDDIVE_SOUND", 5);
    spawnParticles("GROUNDDIVE_PARTICLE", 20);
  }
}
```
Finally, here are the superpowers in active.
```
    LOGGER.info("Use superpower: sky launch");
    var skyLaunch = new SkyLaunch();
    skyLaunch.activate();
    LOGGER.info("Use superpower: ground dive");
    var groundDive = new GroundDive();
    groundDive.activate();
```
Program output:
```
// Use superpower: sky launch
// Move to ( 0.0, 0.0, 20.0 )
// Play SKYLAUNCH_SOUND with volume 1
// Spawn 100 particle with type SKYLAUNCH_PARTICLE
// Use superpower: ground dive
// Move to ( 0.0, 0.0, -20.0 )
// Play GROUNDDIVE_SOUND with volume 5
// Spawn 20 particle with type GROUNDDIVE_PARTICLE
```
## Class diagram
![alt text](./etc/subclass-sandbox.urm.png "Subclass Sandbox pattern class diagram")
  
## Applicability  
The Subclass Sandbox pattern is a very simple, common pattern lurking in lots of codebases, even outside of games. If you have a non-virtual protected method laying around, you’re probably already using something like this. Subclass Sandbox is a good fit when:

-   You have a base class with a number of derived classes.
-   The base class is able to provide all of the operations that a derived class may need to perform.
-   There is behavioral overlap in the subclasses and you want to make it easier to share code between them.
-   You want to minimize coupling between those derived classes and the rest of the program.
  
## Credits  
  
* [Game Programming Patterns - Subclass Sandbox](https://gameprogrammingpatterns.com/subclass-sandbox.html)
