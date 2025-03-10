package edu.hitsz.application;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.game.BaseGame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Controller class for the hero aircraft.
 * Listens to mouse inputs to control the movement of the hero aircraft.
 */
public class HeroController {
    private MouseAdapter mouseAdapter;

    public HeroController(BaseGame baseGame, HeroAircraft heroAircraft) {
        mouseAdapter = new MouseAdapter() {
            /**
             * Implements drag-to-move functionality for the hero aircraft.
             */
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                int newX = e.getX();
                int newY = e.getY();
                // Prevents the aircraft from moving outside the boundaries of the window.
                if (newX >= 0 && newX <= Main.WINDOW_WIDTH && newY >= 0 && newY <= Main.WINDOW_HEIGHT) {
                    heroAircraft.setLocation(newX, newY);
                    // Update the shield location if the shield is active.
                    if (!heroAircraft.shield.notValid()) {
                        heroAircraft.shield.setLocation(newX, newY);
                    }
                }
            }

            /**
             * Implements functionality to pause the game with a mouse click.
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                // Checks if the right mouse button (mouse button 3) was pressed.
                if (e.getButton() == MouseEvent.BUTTON3) {
                    System.out.print("Right mouse button clicked,");
                    if (!BaseGame.pause) {
                        System.out.println("Game paused");
                    } else {
                        System.out.println("Game resumed");
                    }
                    BaseGame.pause = !BaseGame.pause;
                }
            }
        };
        baseGame.addMouseListener(mouseAdapter);
        baseGame.addMouseMotionListener(mouseAdapter);
    }
}

