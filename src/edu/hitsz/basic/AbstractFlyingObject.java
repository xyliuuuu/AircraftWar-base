package edu.hitsz.basic;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

import java.awt.image.BufferedImage;

/**
 * The parent class of flyable objects
 */
public abstract class AbstractFlyingObject {

    protected int locationX;
    protected int locationY;
    protected int speedX;
    protected int speedY;
    protected BufferedImage image; 
    protected int width = -1;
    protected int height = -1;
    protected boolean isValid = true;

    public AbstractFlyingObject(int locationX, int locationY, int speedX, int speedY) {
        this.locationX = locationX;
        this.locationY = locationY;
        this.speedX = speedX;
        this.speedY = speedY;
        loadImage();
    }

    private void loadImage() {
        image = ImageManager.get(this);
        if (image != null) {
            width = image.getWidth();
            height = image.getHeight();
        }
    }

    public void forward() {
        locationX += speedX;
        locationY += speedY;
        if (locationX <= 0 || locationX >= Main.WINDOW_WIDTH) {
            speedX = -speedX;
        }
    }

    public boolean crash(AbstractFlyingObject flyingObject) {
        int thisFactor = this instanceof AbstractAircraft ? 4 : 2; // Simplified scaling factor
        int otherFactor = flyingObject instanceof AbstractAircraft ? 4 : 2;

        int x = flyingObject.getLocationX();
        int y = flyingObject.getLocationY();
        int fWidth = flyingObject.getWidth();
        int fHeight = flyingObject.getHeight();

        return Math.abs(x - this.locationX) * 2 < (fWidth + this.width)
                && Math.abs(y - this.locationY) * 2 < (fHeight / otherFactor + this.height / thisFactor);
    }

    public void vanish() {
        isValid = false;
    }

    public int getLocationX() {
        return locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    public void setLocation(double locationX, double locationY) {
        this.locationX = (int) locationX;
        this.locationY = (int) locationY;
    }

    public int getSpeedY() {
        return speedY;
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean notValid() {
        return !isValid;
    }

    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }
}
