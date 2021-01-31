package com.tmf.snake2d;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import com.tmf.snake2d.model.Apple;
import com.tmf.snake2d.model.Direction;
import com.tmf.snake2d.model.Snake;
import com.tmf.snake2d.model.Snake.SnakePart;

/**
 * Represents the game board where the game takes place.
 * <p>
 * The game board is a square board which size is equal to {@link #SIZE}.
 */
public class GameBoard extends JPanel {

    private static final long serialVersionUID = -959215068747060017L;

    /**
     * Game speed.
     */
    // TODO allow speed to be selected or even vary speed during the game play.
    private static final long GAME_SPEED = 100L;

    /**
     * The size of the board.
     * <p>
     * This value does not defined the {@link JPanel} dimension. Actually, it is the
     * size of a (virtual) {@code size X size} matrix where the game is played and
     * elements are placed. By doing so, the space where the game is played (i.e.
     * the board) is detached from the graphical elements. So, for example, when
     * checking for collisions, it is not necessary to know where elements are in
     * the screen but their position in the (virtual) matrix.
     * <p>
     * The matrix is said "{@code virtual}" because this implementation does not
     * allocate any matrix in the memory.
     */
    public static final int SIZE = 20;

    /**
     * The graphical elements square size.
     */
    private static final int SPRITE_SIZE = 32;

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
     * Indicate the score during a game play.
     */
    private int score;

    /**
     * JLabel where the score will be displayed.
     */
    private JLabel scoreLabel;

    /**
     * The GameBoard constructor method.
     */
    public GameBoard() {
        final int statusBarHeight = 32;

        // The JPanel size is defined by the SIZE multiplied by SPRITE_SIZE. It means
        // that the JPanel must be capable of accommodate SIZE * SPRITE_SIZE game
        // sprites.
        int boardSize = SIZE * SPRITE_SIZE;
        setPreferredSize(new Dimension(boardSize, boardSize + statusBarHeight));

        setFocusable(true);

        // add status bar
        this.setLayout(new BorderLayout());

        JPanel statusBar = new JPanel();
        statusBar.setBorder(new BevelBorder(BevelBorder.LOWERED));
        statusBar.setPreferredSize(new Dimension(boardSize, statusBarHeight));
        statusBar.setLayout(new BoxLayout(statusBar, BoxLayout.X_AXIS));
        this.add(statusBar, BorderLayout.SOUTH);

        JLabel scoreTextLabel = new JLabel("Score: ");
        scoreTextLabel.setHorizontalAlignment(SwingConstants.LEFT);

        scoreLabel = new JLabel("");
        scoreLabel.setHorizontalAlignment(SwingConstants.LEFT);

        statusBar.add(scoreTextLabel);
        statusBar.add(scoreLabel);

        // add KeyListener
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                /* NO-OP */ }

            @Override
            public void keyReleased(KeyEvent e) {
                /* NO-OP */ }

            @Override
            public void keyPressed(KeyEvent e) {
                if (changedDirectionAtCurrentTick)
                    return;

                Direction newDirection;
                switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    newDirection = Direction.UP;
                    break;
                case KeyEvent.VK_DOWN:
                    newDirection = Direction.DOWN;
                    break;
                case KeyEvent.VK_LEFT:
                    newDirection = Direction.LEFT;
                    break;
                case KeyEvent.VK_RIGHT:
                    newDirection = Direction.RIGHT;
                    break;
                default:
                    return;
                }

                changedDirectionAtCurrentTick = snake.changeDirection(newDirection);
            }
        });
    }

    /**
     * Start a new game
     */
    public void init() {
        inGame = true;
        score = 0;
        snake.init(SIZE / 2, SIZE / 2);

        notifyScoreChange();
        nextApple();

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
    public void process() {
        changedDirectionAtCurrentTick = false;

        if (!snake.move()) {
            endGame();
        }

        checkCollisions();

        this.repaint();
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
        int x = snakeHead.getX(), y = snakeHead.getY();
        if (x < 0 || y < 0 || x >= SIZE || y >= SIZE) {
            endGame();

            return;
        }

        if (snakeHead.equals(apple)) {
            score++;
            notifyScoreChange();
            snake.feed();
            nextApple();
        }
    }

    /**
     * Notify that the score changed.
     */
    private void notifyScoreChange() {
        scoreLabel.setText(String.valueOf(score));
    }

    /**
     * Move the apple to the next point and ensure that it does not collide with the
     * snake.
     */
    private void nextApple() {
        do {
            apple.moveToRandomPoint(SIZE);
        } while (!snake.collidesWithSnake(apple));
    }

    /**
     * Notify the end of the game.
     */
    private void endGame() {
        inGame = false;
        timer.cancel();
    }

    @Override
    public void paint(Graphics g) {
        if (!inGame)
            return;

        super.paint(g);

        // paint snake
        Graphics2D g2d = (Graphics2D) g;

        // paint apple
        Ellipse2D appleGraphic = new Ellipse2D.Double(apple.getX() * SPRITE_SIZE, apple.getY() * SPRITE_SIZE,
                SPRITE_SIZE, SPRITE_SIZE);
        g2d.setColor(Color.BLACK);
        g2d.draw(appleGraphic);
        g2d.setColor(Color.RED);
        g2d.fill(appleGraphic);

        // paint snake
        Iterator<SnakePart> iterator = snake.iterator();
        while (iterator.hasNext()) {
            SnakePart snakepart = iterator.next();

            Rectangle2D.Double part = new Rectangle2D.Double(snakepart.getX() * SPRITE_SIZE,
                    snakepart.getY() * SPRITE_SIZE, SPRITE_SIZE, SPRITE_SIZE);
            g2d.setColor(Color.GREEN);
            g2d.fill(part);
        }
    };
}
