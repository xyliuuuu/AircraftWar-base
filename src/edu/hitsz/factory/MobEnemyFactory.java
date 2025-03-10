package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractEnemyAircraft;
import edu.hitsz.aircraft.MobEnemy;
import edu.hitsz.application.game.BaseGame;
import edu.hitsz.strategy.NoneShoot;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

import java.util.Random;

public class MobEnemyFactory implements EnemyFactory {
    private Random random = new Random();

    @Override
    public AbstractEnemyAircraft createEnemy() {
        // Generate random starting positions for the MobEnemy
        int posX = getRandomPositionX();
        int posY = getRandomPositionY();
        
        // Create a MobEnemy with specific attributes and positions
        MobEnemy mobEnemy = new MobEnemy(
                posX,
                posY,
                BaseGame.MOB_SPEED_X,
                BaseGame.MOB_SPEED_Y,
                BaseGame.MOB_HP_BASE,
                0,  // number of lives
                0,  // bullet power
                1   // bullet rate, not used as strategy is NoneShoot
        );
        
        // Set shooting strategy to none (no shooting)
        mobEnemy.setStrategy(new NoneShoot());
        
        return mobEnemy;
    }

    // Calculate a random x-coordinate within the bounds of the window minus the image width
    private int getRandomPositionX() {
        return random.nextInt(Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth());
    }

    // Calculate a random y-coordinate as a small percentage of the window height
    private int getRandomPositionY() {
        return (int) (random.nextDouble() * Main.WINDOW_HEIGHT * 0.05);
    }
}
