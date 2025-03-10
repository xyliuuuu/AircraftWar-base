package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.AbstractEnemyAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class DirectShoot implements Strategy {

    @Override
    public List<BaseBullet> shootExecute(AbstractAircraft aircraft) {
        List<BaseBullet> res = new LinkedList<>();
        int bulletSpeedY = aircraft.getSpeedY() + aircraft.getDirection() * 5;

        for (int i = 0; i < aircraft.getShootNum(); i++) {
            int bulletLocationY = aircraft.getLocationY() + aircraft.getDirection() * 20 * (i + 1);
            res.add(createBullet(aircraft, aircraft.getLocationX(), bulletLocationY, 0, bulletSpeedY));
        }
        return res;
    }

    private BaseBullet createBullet(AbstractAircraft aircraft, int x, int y, int speedX, int speedY) {
        if (aircraft instanceof HeroAircraft) {
            return new HeroBullet(x, y, speedX, speedY, aircraft.getPower());
        } else if (aircraft instanceof AbstractEnemyAircraft) {
            return new EnemyBullet(x, y, speedX, speedY, aircraft.getPower());
        }
        return null; 
    }
}
