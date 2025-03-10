package edu.hitsz.property;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.Main;
import edu.hitsz.basic.AbstractFlyingObject;

public abstract class AbstractProperty extends AbstractFlyingObject {

    private static final int VERTICAL_SPEED = 10;

    public AbstractProperty(int locationX, int locationY) {
        super(locationX, locationY, 0, VERTICAL_SPEED);
    }

    public abstract void propertyActive(HeroAircraft heroAircraft);

    @Override
    public void forward() {
        super.forward();
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish();
        }
    }
}
