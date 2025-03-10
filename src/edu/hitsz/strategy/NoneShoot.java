package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;

import java.util.LinkedList;
import java.util.List;

public class NoneShoot implements Strategy{

    @Override
    public List<BaseBullet> shootExecute(AbstractAircraft aircraft) {
        return new LinkedList<>();
    }
}
