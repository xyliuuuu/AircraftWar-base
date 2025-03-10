package edu.hitsz.property;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.game.BaseGame;
import edu.hitsz.music.MusicThread;

public class BloodProperty extends AbstractProperty {
    private final int BLOOD_UP = 40;

    public BloodProperty(int locationX, int locationY){
        super(locationX, locationY);
    }

    @Override
    public void propertyActive(HeroAircraft heroAircraft) {
        if(BaseGame.musicOn){
            BaseGame.pool.execute(new MusicThread("src/videos/get_supply.wav"));
        }
        int newHp = Math.min(heroAircraft.getHp() + BLOOD_UP, BaseGame.HERO_HP);
        heroAircraft.setHp(newHp);
    }
}

