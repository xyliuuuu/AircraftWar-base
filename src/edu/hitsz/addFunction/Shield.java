package edu.hitsz.addFunction;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.application.game.BaseGame;

/**
 * The hero aircraft can obtain a shield after shooting down a number of enemy aircraft.
 */
public class Shield extends AbstractAircraft {
    /**
     * Constructor
     * @param locationX: X coordinate of the shield
     * @param locationY: Y coordinate of the shield
     */
    public Shield(int locationX, int locationY){
        super(locationX, locationY, 0, 0, BaseGame.SHIELD_HP);
        setValid(false);
    }
}
