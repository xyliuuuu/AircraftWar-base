package edu.hitsz.application.game;

import edu.hitsz.application.ImageManager;

import java.awt.*;
import java.io.File;

public class NormalGame extends BaseGame {

    @Override
    public void initial() {
        setBossScoreThreshold(400);
        ImageManager.BLOOD_BAR_IMAGE = ImageManager.BLOOD_BAR_IMAGE_NORMAL;
        bossBarColor = Color.yellow;
        enemyMaxNumber = 9;
        setDataFilePath(new File("rankNormal.data"));
        setEnemyBulletPower(50);
        setCycleDurationDecrease(4);
        setRateUp(0.03);
        setElitePossibility(0.4f);
    }

    @Override
    public boolean needBossEnemy() {
        return true;
    }
}
