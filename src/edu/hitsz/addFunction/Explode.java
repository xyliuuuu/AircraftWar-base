package edu.hitsz.addFunction;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.application.ImageManager;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Handles the explosion animation after an enemy aircraft is destroyed.
 */
public class Explode {
    private int explosionX;
    private int explosionY;
    /**
     * Index to track which explosion image to display next.
     */
    public int frameIndex = 0;

    /**
     * Constructor initializes the explosion based on the aircraft's position.
     * @param aircraft: The aircraft that will explode.
     */
    public Explode(AbstractAircraft aircraft) {
        // Adjust explosion position to center it on the aircraft due to different image sizes.
    	this.explosionX = aircraft.getLocationX() - aircraft.getWidth() / 2 + aircraft.getWidth() / 6;
        this.explosionY = aircraft.getLocationY() - aircraft.getHeight() / 2 + aircraft.getHeight() / 6;
    }

    /**
     * Draws the explosion effect.
     * @param g: Graphics object for drawing.
     */
    public void draw(Graphics g) {
        if (frameIndex < 16) {
            BufferedImage currentImage = ImageManager.EXPLODE_IMAGE_LIST[frameIndex % ImageManager.EXPLODE_IMAGE_LIST.length];
            g.drawImage(currentImage, explosionX, explosionY, null);
            // Update frame index only if it's an even number to slow down the animation.
            if (frameIndex % 2 == 0) {
                frameIndex++;
            }
            frameIndex++;
        }
    }

    /**
     * Returns the current index of explosion animation frames.
     */
    public int getExplodeCount() {
        return frameIndex;
    }

    /**
     * Determines if the explosion animation has finished.
     */
    public boolean explodeEnd() {
        return frameIndex >= 16;
    }
}