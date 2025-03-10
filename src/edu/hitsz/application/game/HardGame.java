package edu.hitsz.application.game;

import edu.hitsz.application.ImageManager;

import java.awt.*;
import java.io.File;

public class HardGame extends BaseGame {

    @Override
    public void initial() {
        setBossScoreThreshold(300);
        ImageManager.BLOOD_BAR_IMAGE = ImageManager.BLOOD_BAR_IMAGE_HARD;
        bossBarColor = Color.red;
        enemyMaxNumber = 14;
        setDataFilePath(new File("rankDifficult.data"));
        setEnemyBulletPower(60);
        setRateUp(0.04);
        setCycleDurationDecrease(5);
        setElitePossibility(0.5f);
    }

    @Override
    public boolean needBossEnemy() {
        return true;
    }
}
