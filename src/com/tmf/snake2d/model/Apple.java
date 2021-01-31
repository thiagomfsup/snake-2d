package com.tmf.snake2d.model;

import java.util.Random;

/**
 * Represent an apple.
 */
public class Apple extends Point {

    private Random random = new Random();

    public Apple() {
        super();
    }

    public Apple(int x, int y) {
        super(x, y);
    }

    /**
     * Move this object to another randomly chosen point in the Cartesian plan.
     * <p>
     * The chosen point will be limited to {@code (0, 0)} and
     * {@code (limit, limit)}.
     * 
     * @param limit The size of the square that limits the area where the point will
     *              be chosen from.
     */
    public void moveToRandomPoint(int limit) {
        setCoordinate(random.nextInt(limit), random.nextInt(limit));
    }
}
