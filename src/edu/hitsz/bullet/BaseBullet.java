package edu.hitsz.bullet;

import edu.hitsz.application.Main;
import edu.hitsz.property.Subscriber;
import edu.hitsz.basic.AbstractFlyingObject;


public class BaseBullet extends AbstractFlyingObject implements Subscriber {

    private int power = 10; // Damage power of the bullet

    public BaseBullet(int locationX, int locationY, int speedX, int speedY, int power) {
        super(locationX, locationY, speedX, speedY);
        this.power = power;
    }

//  Controls the bullet's movement forward.
    @Override
    public void forward() {
        super.forward();
        checkBounds();
    }

//  Checks if the bullet is out of the game window's bounds and makes it disappear if so.
    private void checkBounds() {
        // X-axis out of bounds
        if (locationX <= 0 || locationX >= Main.WINDOW_WIDTH) {
            vanish();
        }
        // Y-axis out of bounds
        if ((speedY > 0 && locationY >= Main.WINDOW_HEIGHT) || locationY <= 0) {
            vanish();
        }
    }

//  when a bomb explodes, all enemy bullets should disappear.
    @Override
    public void update() {
        vanish(); // Make bullet disappear
    }

//	Gets the power of the bullet.
    public int getPower() {
        return power;
    }
}
