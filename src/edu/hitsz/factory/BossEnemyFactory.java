package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractEnemyAircraft;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.application.game.BaseGame;
import edu.hitsz.strategy.ScatterShoot;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

public class BossEnemyFactory implements EnemyFactory {

    @Override
    public AbstractEnemyAircraft createEnemy() {
        // Generate random starting positions for the BossEnemy
        int posX = getRandomPositionX();
        int posY = getRandomPositionY();
        
        // Create a BossEnemy with specific attributes and positions
        BossEnemy bossEnemy = new BossEnemy(
                posX,
                posY,
                BaseGame.BOSS_SPEED_X,
                BaseGame.BOSS_SPEED_Y,
                BaseGame.bossHp,
                3,  // number of lives
                BaseGame.enemyBulletPower,
                1   // bullet rate
        );
        
        // Set shooting strategy to scatter shoot
        bossEnemy.setStrategy(new ScatterShoot());
        
        return bossEnemy;
    }

    // Calculate a random x-coordinate within the bounds of the window minus the image width
    private int getRandomPositionX() {
        return (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.BOSS_ENEMY_IMAGE.getWidth()));
    }

    // Calculate a random y-coordinate as a small percentage of the window height
    private int getRandomPositionY() {
        return (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05);
    }
}
