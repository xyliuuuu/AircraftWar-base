package edu.hitsz.aircraft;

import edu.hitsz.application.game.BaseGame;
import edu.hitsz.application.Main;
import edu.hitsz.property.AbstractProperty;

import java.util.List;

/**
 * Normal enemy aircraft
 * Incapable of shooting
 */
public class MobEnemy extends AbstractEnemyAircraft {

    /**
     * Constructor
     * @param locationX: X coordinate
     * @param locationY: Y coordinate
     * @param speedX: Speed on the X axis
     * @param speedY: Speed on the Y axis
     * @param hp: Health points
     * @param shootNum: Number of bullets fired (not used here as it cannot shoot)
     * @param power: Damage per bullet (not used here as it cannot shoot)
     * @param direction: Direction (not used here as it cannot shoot)
     */
    public MobEnemy(int locationX, int locationY, int speedX, int speedY, int hp, int shootNum, int power, int direction) {
        super(locationX, locationY, speedX, speedY, hp, shootNum, power, direction);
    }

    /**
     * Controls the forward movement of the normal enemy aircraft
     */
    @Override
    public void forward() {
        super.forward();
        // Check if the aircraft flies beyond the bottom edge of the window
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish();
        }
    }

    /**
     * Generates a score bonus.
     * Since normal enemy aircraft do not drop properties, this is a no-op for properties.
     * @param properties: Unused as this enemy does not drop properties
     * @param locationX: Unused as this enemy does not drop properties
     * @param locationY: Unused as this enemy does not drop properties
     */
    @Override
    public void dropProperty(List<AbstractProperty> properties, int locationX, int locationY) {
        Main.baseGame.increaseScore(BaseGame.MOB_SCORE_UP);
    }

    /**
     * Implements the observer pattern.
     * Normal enemy aircraft acts as an observer, the bomb property is the observed target. 
     * When the bomb property takes effect, the normal enemy aircraft dies, generating the corresponding score and an explosion.
     */
    @Override
    public void update() {
        this.decreaseHp(BaseGame.MOB_HP_BASE);
        if (hp <= 0) {
            Main.baseGame.increaseScore(BaseGame.MOB_SCORE_UP);
            Main.baseGame.addExplodeList(this);
        }
    }
}

