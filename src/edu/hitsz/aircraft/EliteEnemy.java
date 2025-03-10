package edu.hitsz.aircraft;

import edu.hitsz.application.game.BaseGame;
import edu.hitsz.application.Main;

import edu.hitsz.factory.BloodPropertyFactory;
import edu.hitsz.factory.BulletPropertyFactory;
import edu.hitsz.factory.PropertyFactory;
import edu.hitsz.property.AbstractProperty;

import java.util.List;

/**
 * Elite enemy aircraft
 * Capable of shooting
 * Possesses random horizontal speed
 */
public class EliteEnemy extends AbstractEnemyAircraft {
    /**
     * Constructor
     * @param locationX: X coordinate
     * @param locationY: Y coordinate
     * @param speedX: X-axis speed
     * @param speedY: Y-axis speed
     * @param hp: Health points
     * @param shootNum: Number of bullets shot
     * @param power: Bullet damage
     * @param direction: Direction
     */
    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp, int shootNum, int power, int direction) {
        super(locationX, locationY, speedX, speedY, hp, shootNum, power, direction);
    }

    /**
     * Controls the elite enemy aircraft to move forward
     */
    @Override
    public void forward() {
        super.forward();
        // Determine if the aircraft flies out of bounds on the Y-axis downward
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish();
        }
    }

    /**
     * Generates properties and score bonuses
     * @param properties: List of properties, the generated properties are added to it
     * @param locationX: X coordinate where properties are generated
     * @param locationY: Y coordinate where properties are generated
     */
    @Override
    public void dropProperty(List<AbstractProperty> properties, int locationX, int locationY){
        Main.baseGame.increaseScore(BaseGame.ELITE_SCORE_UP);
        // Using factory method pattern to create properties
        PropertyFactory propertyFactory;
        double randomNumber = Math.random(); // Using Math.random() for generating random double
        if (randomNumber <= BaseGame.propertyDropPossibility) {
            propertyFactory = new BloodPropertyFactory();
        } else if (randomNumber > BaseGame.propertyDropPossibility && randomNumber <= BaseGame.propertyDropPossibility * 2) {
            propertyFactory = new BulletPropertyFactory();
        } else {
            propertyFactory = null;  // Ensuring null is explicitly set for clarity
        }
        if (propertyFactory != null) {
            properties.add(propertyFactory.createProperty(locationX, locationY));
        }
    }

    /**
     * Using observer pattern
     * Elite enemy aircraft acts as an observer, bomb property is the observed target. When bomb property takes effect, the elite enemy aircraft dies, generating corresponding score, produces explosion, but does not generate properties.
     */
    @Override
    public void update() {
        this.decreaseHp(BaseGame.eliteHp);
        if (hp <= 0) {  // Ensuring we only score and explode if hp falls below or equal to 0
            Main.baseGame.increaseScore(BaseGame.ELITE_SCORE_UP);
            Main.baseGame.addExplodeList(this);
        }
    }
}

