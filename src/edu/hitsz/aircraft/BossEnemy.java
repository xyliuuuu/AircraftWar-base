package edu.hitsz.aircraft;

import edu.hitsz.application.game.BaseGame;
import edu.hitsz.application.Main;
import edu.hitsz.factory.BloodPropertyFactory;
import edu.hitsz.factory.BulletPropertyFactory;
import edu.hitsz.factory.PropertyFactory;
import edu.hitsz.property.AbstractProperty;

import java.util.List;
import java.util.Random;

public class BossEnemy extends AbstractEnemyAircraft {
    /**
     * Number of items dropped by the boss aircraft
     */
    private static final int BOSS_PROPERTY_DROP_NUM = 2;

    /**
     * Constructor
     * @param locationX: X coordinate
     * @param locationY: Y coordinate
     * @param speedX: X speed
     * @param speedY: Y speed
     * @param hp: Health points
     * @param shootNum: Number of bullets fired
     * @param power: Bullet damage
     * @param direction: Direction
     */
    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp, int shootNum, int power, int direction) {
        super(locationX, locationY, speedX, speedY, hp, shootNum, power, direction);
    }

    /**
     * Control the movement of the boss enemy aircraft
     * Boss enemy aircraft only move horizontally, speedY=0
     */
    @Override
    public void forward() {
        super.forward();
    }

    /**
     * Generate items and score bonuses
     * Boss enemy aircraft generate two random items at once
     * @param properties: List of properties, the generated items will be added to it
     * @param locationX: X coordinate where the items are generated
     * @param locationY: Y coordinate where the items are generated
     */
    @Override
    public void dropProperty(List<AbstractProperty> properties, int locationX, int locationY) {
        // Increase the score when the boss drops items
        Main.baseGame.increaseScore(BaseGame.BOSS_SCORE_UP);

        // Use a single Random instance for better performance and less redundancy
        Random random = new Random();
        PropertyFactory propertyFactory;
        for (int i = 0; i < BOSS_PROPERTY_DROP_NUM; i++) {
            // Simplify property selection using ternary operator
            propertyFactory = random.nextDouble() > 0.5 ? new BulletPropertyFactory() : new BloodPropertyFactory();

            // Calculate offset for each dropped item to prevent stacking
            int offsetX = (i - BOSS_PROPERTY_DROP_NUM / 2) * 30;
            properties.add(propertyFactory.createProperty(locationX + offsetX, locationY));
        }
    }

    /**
     * Using the observer pattern
     * Boss enemy aircraft is the observer, bomb property is the observed target.
     * When the bomb property takes effect, the boss enemy aircraft loses 1/3 of its health points,
     * generates corresponding score, produces explosion, but doesn't generate items.
     */
    @Override
    public void update() {
        this.decreaseHp(BaseGame.BOSS_HP_BASE / 3);
        // If boss aircraft is destroyed, generate score and explosion effect
        if(hp<=0){
            Main.baseGame.increaseScore(BaseGame.BOSS_SCORE_UP);
            Main.baseGame.addExplodeList(this);
        }
    }
}
