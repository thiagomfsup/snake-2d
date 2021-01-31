package com.tmf.snake2d.model;

/**
 * Represent a Point in a Cartesian plane.
 * <p>
 * {@code Point} is an immutable class. However, subclasses are allowed to
 * change their position in the Cartesian plan.
 */
public class Point {

    private int x;

    private int y;

    /**
     * Create a Point at {@code (0, 0)} position.
     */
    public Point() {
        // explicit empty.
    }

    /**
     * Create a Point at {@code (x, y)} position.
     * 
     * @param x The {@code x} position.
     * @param y The {@code y} position.
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object other) {
        return (this == other)
                || ((other instanceof Point) && (((Point) other).x == this.x) && (((Point) other).y == this.y));
    }

    @Override
    public String toString() {
        return String.format("{x: %d, y: %d}", x, y);
    }

    /**
     * Change this point position based on the {@code x} and {@code y} params.
     * 
     * @param x The new {@code x} position.
     * @param y The new {@code y} position.
     */
    protected void setCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Change this point position based on the {@link Point} param.
     * 
     */
    protected void setCoordinate(Point p) {
        setCoordinate(p.x, p.y);
    }
}
