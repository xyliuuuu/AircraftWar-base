package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.strategy.Strategy;

import java.util.List;

/**
 * Abstract parent class for all types of aircraft:
 * Enemies (BOSS, ELITE, MOB), and the hero aircraft.
 *
 */
public abstract class AbstractAircraft extends AbstractFlyingObject {
    /**
     * Current health points
     */
    protected int hp;
    /**
     * Maximum health points
     */
    protected int maxHp;

    /**
     * Number of bullets fired per shot
     */
    protected int shootNum;

    /**
     * Bullet damage
     */
    protected int power;
    /**
     * Direction of bullet firing (Upward: 1, Downward: -1)
     */
    protected int direction;

    /**
     * Using the strategy pattern, this class is the context class, holding a reference to a strategy.
     */
    private Strategy strategy;

    /**
     * Constructor
     *
     * @param locationX: X coordinate
     * @param locationY: Y coordinate
     * @param speedX: X direction speed
     * @param speedY: Y direction speed
     * @param hp: Health points
     */
    public AbstractAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY);
        this.hp = hp;
        this.maxHp = hp;
    }

    /**
     * Decrease health points when attacked
     *
     * @param decreaseNum: Amount of health points decreased
     */
    public void decreaseHp(int decreaseNum) {
        // Ensure that the decrease number is not negative to prevent increasing hp inadvertently
        if (decreaseNum < 0) {
            System.err.println("Invalid decrease amount: " + decreaseNum);
            return;
        }

        // Subtract the decrease amount from hp
        hp -= decreaseNum;

        // Ensure hp does not drop below zero
        if (hp <= 0) {
            hp = 0;
            // If hp is zero or less, the aircraft vanishes
            vanish();
        }
    }

    /**
     * Using the strategy pattern, set the shooting strategy
     *
     * @param strategy: Shooting strategy
     */
    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Shoot
     *
     * @param aircraft: Aircraft shooting
     * @return List of bullets shot
     */
    public List<BaseBullet> shoot(AbstractAircraft aircraft) {
        return strategy.shootExecute(aircraft);
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int num) {
        hp = num;
    }

    public int getShootNum() {
        return shootNum;
    }

    public int getPower() {
        return power;
    }

    public int getDirection() {
        return direction;
    }

    public void setShootNum(int shootNum) {
        this.shootNum = shootNum;
    }
}



