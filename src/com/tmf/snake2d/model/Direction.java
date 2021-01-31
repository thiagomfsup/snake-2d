package com.tmf.snake2d.model;

/**
 * Represent a direction.
 */
public enum Direction {

    UP, RIGHT, DOWN, LEFT;

    public boolean isOpposite(Direction other) {

        // FIXME can't it be simpler?
        return (this == UP && other == DOWN) || (this == DOWN && other == UP) || (this == RIGHT && other == LEFT)
                || (this == LEFT && other == RIGHT);
    }

}