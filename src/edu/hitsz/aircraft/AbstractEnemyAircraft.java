package edu.hitsz.aircraft;

import edu.hitsz.property.Subscriber;
import edu.hitsz.property.AbstractProperty;

import java.util.List;

/**
 * Abstract enemy aircraft class
 * Enemies include regular enemies (MobEnemy), elite enemies (EliteEnemy), and bosses.
 * Enemy aircraft need to implement the method of dropping items.
 */
public abstract class AbstractEnemyAircraft extends AbstractAircraft implements Subscriber {
    /**
     * Constructor
     *
     * @param locationX: X coordinate
     * @param locationY: Y coordinate
     * @param speedX: X direction speed
     * @param speedY: Y direction speed
     * @param hp: Health points
     * @param shootNum: Number of bullets fired per shot
     * @param power: Bullet damage
     * @param direction: Shooting direction
     */
    public AbstractEnemyAircraft(int locationX, int locationY, int speedX, int speedY, int hp, int shootNum, int power, int direction) {
        super(locationX, locationY, speedX, speedY, hp);
        this.shootNum = shootNum;
        this.power = power;
        this.direction = direction;
    }

    /**
     * Method for dropping items
     *
     * @param properties: List of properties, dropped items will be added to this list
     * @param locationX: X coordinate where the item drops
     * @param locationY: Y coordinate where the item drops
     */
    public abstract void dropProperty(List<AbstractProperty> properties, int locationX, int locationY);
}
