package com.tmf.snake2d;

import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import com.tmf.snake2d.model.Apple;
import com.tmf.snake2d.model.Direction;
import com.tmf.snake2d.model.Point;
import com.tmf.snake2d.model.Snake;
import com.tmf.snake2d.model.Snake.SnakePart;

/**
 * Responsible to control the game play.
 */
public class GameController {

    /**
     * Game speed.
     */
    // TODO allow speed to be selected or even vary speed during the game play.
    private static final long GAME_SPEED = 140L;

    private GameBoard gameBoard;

    private Snake snake = new Snake();

    private Apple apple = new Apple();

    /**
     * The {@link Timer} that controls the clock tick.
     */
    private Timer timer;

    /**
     * Flag to prevent changing direction more than once at the same clock tick.
     */
    private boolean changedDirectionAtCurrentTick = false;

    /**
     * Flag that indicates if the game is being played.
     */
    private boolean inGame;

    /**
     * Flag that indicates the game is paused.
     */
    private boolean paused;

    /**
     * Indicate the score during a game play.
     */
    private int score;

    public GameController(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    /**
     * Start a new game
     */
    public void init() {
        if (inGame)
            return;

        inGame = true;
        paused = false;
        score = 0;
        snake.init(0,0);

        gameBoard.notifyScoreChange(score);
        nextApple();

        registerTimer();
    }

    private void registerTimer() {
        if (timer != null)
            timer.cancel();

        timer = new Timer("board-timer");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                process();
            }
        }, 0L, GAME_SPEED);
    }

    /**
     * Process the game play.
     * <p>
     * It is supposed to run at each clock tick.
     */
    private void process() {
        changedDirectionAtCurrentTick = false;

        if (!snake.move()) {
            endGame();
        }

        checkCollisions();

        gameBoard.repaint();
    }

    /**
     * Check if:
     * <ul>
     * <li>the Snake is still on the board.
     * <li>the Snake eats an apple.
     * </ul>
     */
    private void checkCollisions() {
        SnakePart snakeHead = snake.head();

        // check if snakeHead is still on the board.
        int x = snakeHead.getPositionX(), y = snakeHead.getPositionY();
        if (x < 0 || y < 0 || x >= GameBoard.SIZE || y >= GameBoard.SIZE) {
            endGame();

            return;
        }

        if (snakeHead.collidesWith(apple)) {
            gameBoard.notifyScoreChange(++score);
            snake.feed();
            nextApple();
        }
    }

    /**
     * Move the apple to the next point and ensure that it does not collide with the
     * snake.
     */
    private void nextApple() {
        do {
            apple.moveToRandomPoint(GameBoard.SIZE);
        } while (snake.collidesWith(apple));
    }

    /**
     * Notify the end of the game.
     */
    private void endGame() {
        inGame = false;
        paused = false;
        timer.cancel();
    }

    private void pauseGame() {
        inGame = false;
        paused = true;
        timer.cancel();
    }

    private void resumeGame() {
        inGame = true;
        paused = false;

        registerTimer();
    }

    /**
     * Notify that Snake should take another direction.
     *
     * @param newDirection The new direction.
     */
    public void notifyChangeDirection(Direction newDirection) {
        if (changedDirectionAtCurrentTick)
            return;

        snake.changeDirection(newDirection);
    }

    /**
     * Verify if a game play is taking place.
     *
     * @return
     */
    public boolean isInGame() {
        return inGame;
    }

    /**
     * Get the Apple position.
     *
     * @return The Apple position.
     */
    public Point getApplePosition() {
        // IMPLEMENTATION NOTE: since Point class is "immutable", returning Apple as a
        // Point avoid it to be changed outside this class.
        return apple;
    }

    /**
     * Return an {@link Iterator} that allows traversing through a Snake parts.
     *
     * @return
     */
    public Iterator<SnakePart> getSnakeIterator() {
        return snake.iterator();
    }

    public void pauseOrResume() {
        if (paused)
            resumeGame();
        else
            pauseGame();
    }
}
