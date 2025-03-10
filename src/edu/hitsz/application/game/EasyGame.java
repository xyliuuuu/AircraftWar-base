package edu.hitsz.application.game;

import java.io.File;

public class EasyGame extends BaseGame {

    @Override
    public void initial() {
        enemyMaxNumber = 7;
        setDataFilePath(new File("rankEasy.data"));
        setEnemyBulletPower(35);
        setRateUp(0);
        setCycleDurationDecrease(0);
        setElitePossibility(0.3f);
    }

    @Override
    public boolean needBossEnemy() {
        return false;
    }
}
