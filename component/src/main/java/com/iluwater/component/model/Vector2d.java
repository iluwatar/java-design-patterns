package com.iluwater.component.model;

public class Vector2d {
    /** x of position. */
    private float x;
    /** y of position. */
    private float y;
    /**
     * Constructor for Vector2d.
     * @param positionX is the x of position
     * @param positionY is the y of position
     */
    public Vector2d(final float positionX, final float positionY) {
        this.x = positionX;
        this.y = positionY;
    }
    /**
     * Change Vector2d to String.
     */
    @Override
    public String toString() {
        return String.format("Vector2d[%.2f, %.2f]", this.x, this.y);
    }
    /**
     * get method for private attribute.
     * @return x
     */
    public float getX() {
        return x;
    }
    /**
     * set method for private attribute.
     * @param positionX
     */
    public void setX(final float positionX) {
        this.x = positionX;
    }
    /**
     * get method for private attribute.
     * @return y
     */
    public float getY() {
        return y;
    }
    /**
     * set method for private attribute.
     * @param positionY
     */
    public void setY(final float positionY) {
        this.y = positionY;
    }
}
