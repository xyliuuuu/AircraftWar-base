package edu.hitsz.application;

import edu.hitsz.application.game.BaseGame;

import javax.swing.*;
import java.awt.*;

/**
 * Program entry point
 *
 * @author hitsz
 */
public class Main {
    /**
     * Window size
     */
    public static final int WINDOW_WIDTH = 512;
    public static final int WINDOW_HEIGHT = 768;

    /**
     * Used for switching windows
     */
    public static final CardLayout cardLayout = new CardLayout(0, 0);
    public static final JPanel cardPanel = new JPanel(cardLayout);

    /**
     * Since there can only be one Game, it is set as a static variable
     */
    public static BaseGame baseGame;

    /**
     * Main method
     *
     * @param args: command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Let's go! Aircraft War");
        // Get screen resolution and initialize Frame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame("Aircraft War");
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setResizable(false);
        // Set window size and position, centering it
        frame.setBounds(((int) screenSize.getWidth() - WINDOW_WIDTH) / 2, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(cardPanel);
        // Load start menu
        StartMenu startMenu = new StartMenu();
        cardPanel.add(startMenu.getMainPanel());
        frame.setVisible(true);
    }
}
