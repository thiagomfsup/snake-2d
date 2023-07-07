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

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import com.tmf.snake2d.model.Direction;
import com.tmf.snake2d.model.Point;
import com.tmf.snake2d.model.Snake.SnakePart;

/**
 * Represents the game board where the game takes place.
 * <p>
 * The game board is a square board which size is equal to {@link #SIZE}.
 */
public class GameBoard extends JPanel {

    private static final long serialVersionUID = -959215068747060017L;

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

    /**
     * JLabel where the score will be displayed.
     */
    private JLabel scoreLabel;

    /**
     * The game controller.
     */
    private GameController gameController;

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
                switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    gameController.notifyChangeDirection(Direction.UP);
                    break;
                case KeyEvent.VK_DOWN:
                    gameController.notifyChangeDirection(Direction.DOWN);
                    break;
                case KeyEvent.VK_LEFT:
                    gameController.notifyChangeDirection(Direction.LEFT);
                    break;
                case KeyEvent.VK_RIGHT:
                    gameController.notifyChangeDirection(Direction.RIGHT);
                    break;
                case KeyEvent.VK_F2:
                    gameController.pauseOrResume();
                    break;
                case KeyEvent.VK_F3:
                    gameController.init();
                    break;
                }
            }
        });
    }

    /**
     * Set the GameController.
     * 
     * @param gameController
     */
    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    /**
     * Notify that the score changed.
     */
    public void notifyScoreChange(int score) {
        scoreLabel.setText(String.valueOf(score));
    }

    @Override
    public void paint(Graphics g) {
        if (!gameController.isInGame())
            return;

        super.paint(g);

        // paint snake
        Graphics2D g2d = (Graphics2D) g;

        // paint apple
        Point apple = gameController.getApplePosition();
        Ellipse2D appleGraphic = new Ellipse2D.Double(apple.getX() * SPRITE_SIZE, apple.getY() * SPRITE_SIZE,
                SPRITE_SIZE, SPRITE_SIZE);
        g2d.setColor(Color.BLACK);
        g2d.draw(appleGraphic);
        g2d.setColor(Color.RED);
        g2d.fill(appleGraphic);

        // paint snake

        Iterator<SnakePart> iterator = gameController.getSnakeIterator();
        while (iterator.hasNext()) {
            SnakePart snakepart = iterator.next();

            Rectangle2D.Double part = new Rectangle2D.Double(snakepart.getPositionX() * SPRITE_SIZE,
                    snakepart.getPositionY() * SPRITE_SIZE, SPRITE_SIZE, SPRITE_SIZE);
            g2d.setColor(new Color(30, 70, 32));
            g2d.fill(part);
        }
    };
}
