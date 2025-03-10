package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class ScatterShoot implements Strategy {

    @Override
    public List<BaseBullet> shootExecute(AbstractAircraft aircraft) {
        List<BaseBullet> res = new LinkedList<>();
        int speedY = aircraft.getSpeedY() + aircraft.getDirection() * 5;

        for (int i = 0; i < aircraft.getShootNum(); i++) {
            int offsetX = (i * 2 - aircraft.getShootNum() + 1) * 10;
            int speedX = (i - 1) * (aircraft instanceof BossEnemy ? 2 : 1);
            int bulletX = aircraft.getLocationX() + offsetX;
            int bulletY = aircraft.getLocationY() + aircraft.getDirection() * 2;

            BaseBullet bullet = createBullet(aircraft, bulletX, bulletY, speedX, speedY);
            res.add(bullet);
        }
        return res;
    }

    private BaseBullet createBullet(AbstractAircraft aircraft, int x, int y, int speedX, int speedY) {
        if (aircraft instanceof HeroAircraft) {
            return new HeroBullet(x, y, speedX, speedY, aircraft.getPower());
        } else if (aircraft instanceof BossEnemy) {
            return new EnemyBullet(x, y, speedX, speedY, aircraft.getPower());
        }
        return null;
    }
}
