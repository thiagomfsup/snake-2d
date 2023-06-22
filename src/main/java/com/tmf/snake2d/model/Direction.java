package com.tmf.snake2d.model;

/**
 * Represent a direction.
 */
public enum Direction {

    UP { public boolean isOpposite(Direction other) { return DOWN == other; } },
    RIGHT { public boolean isOpposite(Direction other) { return LEFT == other; } },
    DOWN { public boolean isOpposite(Direction other) { return UP == other; } },
    LEFT { public boolean isOpposite(Direction other) { return RIGHT == other; } };

    public abstract boolean isOpposite(Direction other);
}
