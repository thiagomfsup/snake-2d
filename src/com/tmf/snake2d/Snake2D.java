package com.tmf.snake2d;

import java.awt.EventQueue;

import javax.swing.JFrame;

/**
 * The Snake 2D main class.
 * 
 * @author Thiago M. Ferreira
 *
 */
public class Snake2D extends JFrame {

    private static final long serialVersionUID = -3296925391476522968L;

    public Snake2D() {
        GameBoard gameBoard = new GameBoard();
        setContentPane(gameBoard);
        pack();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Snake 2D");

        GameController gameController = new GameController(gameBoard);
        gameBoard.setGameControler(gameController);

        gameController.init();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Snake2D snake2d = new Snake2D();
            snake2d.setVisible(true);
        });
    }

}
