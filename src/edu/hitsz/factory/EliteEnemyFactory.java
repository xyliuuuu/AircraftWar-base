package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractEnemyAircraft;
import edu.hitsz.application.game.BaseGame;
import edu.hitsz.strategy.DirectShoot;
import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

import java.util.Random;

public class EliteEnemyFactory implements EnemyFactory {
    private Random random = new Random();

    @Override
    public AbstractEnemyAircraft createEnemy() {
        // Create an EliteEnemy with random position and speed settings
        EliteEnemy eliteEnemy = new EliteEnemy(
                getRandomPositionX(),
                getRandomPositionY(),
                getRandomSpeedX(),
                BaseGame.eliteSpeedY,
                BaseGame.eliteHp,
                1,
                BaseGame.enemyBulletPower,
                1
        );
        // Set shooting strategy to direct shoot
        eliteEnemy.setStrategy(new DirectShoot());
        return eliteEnemy;
    }

    // Generate a random X position within the game window, adjusted for the enemy image width
    private int getRandomPositionX() {
        return random.nextInt(Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth() + 1);
    }

    // Generate a random Y position within a small fraction of the game window height
    private int getRandomPositionY() {
        return (int) (random.nextDouble() * Main.WINDOW_HEIGHT * 0.05);
    }

    // Generate a random horizontal speed between -2 and +1
    private int getRandomSpeedX() {
        return random.nextInt(4) - 2;  // This returns an integer between -2 (inclusive) and 2 (exclusive)
    }
}
