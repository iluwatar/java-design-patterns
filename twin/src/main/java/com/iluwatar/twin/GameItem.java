

package com.iluwatar.twin;

/**
 * GameItem is a common class which provides some common methods for game object.
 */
public abstract class GameItem {

  public abstract void draw();

  public abstract boolean intersects(GameItem other);

  public abstract boolean collideWith(GameItem other);

  /**
   * Template method, check whether this game item is collide with other game item
   * 
   * @param other
   * @return
   */
  public boolean check(GameItem other) {

    if (intersects(other)) {
      return collideWith(other);
    }

    return false;
  }

  public abstract void click();
}
