package com.tmf.snake2d.model;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

public class Snake {

    private final static int INITIAL_SIZE = 3;

    private Deque<SnakePart> parts;

    private Direction currentDirection;

    public Snake() {
        parts = new ArrayDeque<>();
    }

    /**
     * Initiate snake at a given position
     * 
     * @param posX
     * @param posY
     */
    public void init(int posX, int posY) {
        parts.clear();
        for (int i = 0; i < INITIAL_SIZE; i++) {
            SnakePart part = new SnakePart(posX - i, posY);

            parts.addLast(part);
        }

        currentDirection = Direction.RIGHT;
    }

    /**
     * Causes snake to change direction.
     * <p>
     * The new direction can be rejected (i.e. returns {@code false}) if (1) it is
     * equal to the previous direction or (2) the new direction is opposite in
     * comparison with the previous one (e.g previous direction is
     * {@link Direction#UP} and the new direction is {@link Direction#DOWN}).
     * 
     * @param newDirection The new direction param.
     * @return {@code true} if direction has been changed; {@code false} otherwise.
     */
    public boolean changeDirection(Direction newDirection) {
        if ((currentDirection != newDirection) && !currentDirection.isOpposite(newDirection)) {
            currentDirection = newDirection;
            return true;
        }
        return false;
    }

    /**
     * Move snake to the next position depending on the direction previously set.
     * <p>
     * If the next movement causes the snake to collides with itself, it stops
     * moving.
     * 
     * @return {@code false} if it detects that the snake collides with itself,
     *         {@code true} otherwise.
     */
    public boolean move() {
        SnakePart futureHead = new SnakePart();
        futureHead.setCoordinate(parts.getFirst());

        // check direction
        switch (currentDirection) {
        case UP:
            futureHead.moveUp();
            break;
        case DOWN:
            futureHead.moveDown();
            break;
        case RIGHT:
            futureHead.moveRight();
            break;
        case LEFT:
            futureHead.moveLeft();
            break;
        }

        // Check if the future position SnakePart collides with another SnakePart.
        // If collision is detected, return quickly.
        if (parts.stream().filter(snakePart -> snakePart.equals(futureHead)).count() != 0)
            return false;

        // remove tail
        parts.removeLast();

        parts.addFirst(futureHead);

        return true;
    }

    /**
     * Check if the point {@code p} collides with any of the snake part.
     * 
     * @param p The {@code p} point.
     * @return {@code true} if the point {@code p} collides with the snake;
     *         {@code false} otherwise.
     */
    public boolean collidesWithSnake(Point p) {
        return parts.stream().filter(snakePart -> snakePart.equals(p)).count() == 0;
    }

    /**
     * Feeding a snake makes it grow by one {@link SnakePart}.
     */
    public void feed() {
        // just put a new part at the end of the queue. Part position will be computed
        // in the next movement.
        parts.addLast(new SnakePart());
    }

    /**
     * Allows the snake's head to be accessed.
     * 
     * @return the {@link SnakePart} representing the snake head.
     */
    public SnakePart head() {
        // retrieve but not remove.
        return parts.getFirst();
    }

    // TODO why Iterator? should Snake implements Iterable?
    public Iterator<SnakePart> iterator() {
        return parts.iterator();
    }

    /**
     * Represents a snake part.
     */
    public class SnakePart extends Point {
        private SnakePart() {
            // TODO why can't it be (0,0). Why this position get printed on the board.
            super(-1, -1);
        }

        private SnakePart(int x, int y) {
            super(x, y);
        }

        private void moveUp() {
            setCoordinate(this.getX(), this.getY() - 1);
        }

        private void moveDown() {
            setCoordinate(this.getX(), this.getY() + 1);
        }

        private void moveLeft() {
            setCoordinate(this.getX() - 1, this.getY());
        }

        private void moveRight() {
            setCoordinate(this.getX() + 1, this.getY());
        }
    }
}
