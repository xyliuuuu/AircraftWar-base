package edu.hitsz.application;

import edu.hitsz.application.game.BaseGame;
import edu.hitsz.application.game.EasyGame;
import edu.hitsz.application.game.HardGame;
import edu.hitsz.application.game.NormalGame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Start menu for Aircraft War game with an iOS-style aesthetic, using a premium color scheme.
 */
public class StartMenu {
    private JButton easy;
    private JButton normal;
    private JButton hard;
    private JPanel upPanel;
    private JPanel lowPanel;
    public JPanel mainPanel;
    private JLabel title;
    private JLabel label;
    private JComboBox<String> choose;

    public static final int WINDOW_WIDTH = 512;
    public static final int WINDOW_HEIGHT = 768;

    /**
     * Constructor
     */
    public StartMenu() {
        mainPanel = new JPanel(new BorderLayout());
        upPanel = new JPanel(new GridBagLayout());
        lowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Initialize and style buttons with a rounded, premium look
        easy = new JButton("Easy");
        normal = new JButton("Normal");
        hard = new JButton("Hard");

        // Customize button appearance to match a premium, iOS-like style
        styleButton(easy);
        styleButton(normal);
        styleButton(hard);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Add listeners to buttons
        easy.addActionListener(e -> {
            System.out.println("Easy mode started");
            startGame("bg.jpg", 0);
        });
        normal.addActionListener(e -> {
            System.out.println("Normal mode started");
            startGame("bg.jpg", 1);
        });
        hard.addActionListener(e -> {
            System.out.println("Hard mode started");
            startGame("bg.jpg", 2);
        });

        // Add buttons to the panel
        upPanel.add(easy, gbc);
        upPanel.add(normal, gbc);
        upPanel.add(hard, gbc);

        // Style the title label for a clean iOS look
        title = new JLabel("Aircraft War", SwingConstants.CENTER);
        title.setFont(new Font("Cooper Black", Font.BOLD, 30));
        title.setOpaque(true);
        title.setBackground(new Color(230, 230, 250)); // Light lavender
        title.setForeground(new Color(80, 80, 80)); // Dark gray
        title.setPreferredSize(new Dimension(WINDOW_WIDTH, 60));
        mainPanel.add(title, BorderLayout.NORTH);

        // Background music settings label and dropdown
        label = new JLabel("Background Music Setting:");
        String[] options = {"On", "Off"};
        choose = new JComboBox<>(options);
        styleComboBox(choose);
        lowPanel.add(label);
        lowPanel.add(choose);

        // Set background colors
        upPanel.setBackground(new Color(240, 240, 255)); // Very light blue
        lowPanel.setBackground(new Color(240, 240, 255));
        mainPanel.setBackground(new Color(240, 240, 255));

        mainPanel.add(upPanel, BorderLayout.CENTER);
        mainPanel.add(lowPanel, BorderLayout.SOUTH);
    }

    /**
     * Styles buttons with a rounded, premium iOS aesthetic.
     */
    private void styleButton(JButton button) {
        button.setFont(new Font("Cooper Black", Font.PLAIN, 18));
        button.setOpaque(true);
        button.setBackground(new Color(210, 210, 230)); // Soft blue
        button.setForeground(Color.DARK_GRAY);
        button.setPreferredSize(new Dimension(160, 60)); // Increased size
        button.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        button.setFocusPainted(false);
        // Rounded corners
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        button.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 220), 1));
    }

    /**
     * Styles the JComboBox to fit the premium iOS aesthetic.
     */
    private void styleComboBox(JComboBox<String> comboBox) {
        comboBox.setFont(new Font("Cooper Black", Font.PLAIN, 16));
        comboBox.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 220), 1));
    }

    /**
     * Starts the game according to the difficulty level.
     */
    private void startGame(String backgroundPicture, int difficulty) {
        try {
            ImageManager.BACKGROUND_IMAGE = ImageIO.read(new FileInputStream("src/images/" + backgroundPicture));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        switch (difficulty) {
            case BaseGame.EASY:
                Main.baseGame = new EasyGame();
                break;
            case BaseGame.NORMAL:
                Main.baseGame = new NormalGame();
                break;
            case BaseGame.HARD:
                Main.baseGame = new HardGame();
                break;
            default:
        }

        BaseGame.setGameDifficulty(difficulty);
        Main.cardPanel.add(Main.baseGame);
        Main.cardLayout.next(Main.cardPanel);
        if (choose.getSelectedIndex() == 1) {
            BaseGame.musicOn = false;
            System.out.println("Background music turned off");
        } else {
            BaseGame.musicOn = true;
            System.out.println("Background music turned on");
        }
        Main.baseGame.action();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}





