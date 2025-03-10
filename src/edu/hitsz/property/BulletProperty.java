package edu.hitsz.property;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.game.BaseGame;
import edu.hitsz.music.MusicThread;
import edu.hitsz.strategy.DirectShoot;
import edu.hitsz.strategy.ScatterShoot;

import java.util.concurrent.TimeUnit;

public class BulletProperty extends AbstractProperty{
    private static volatile long lastActivationTime = 0;

    public BulletProperty(int locationX, int locationY) {
        super(locationX, locationY);
    }

    @Override
    public void propertyActive(HeroAircraft heroAircraft) {
        long activationTime = System.currentTimeMillis();
        lastActivationTime = activationTime;

        System.out.println("FireSupply active!");
        if(BaseGame.musicOn){
            BaseGame.pool.execute(new MusicThread("src/videos/get_supply.wav"));
        }
        heroAircraft.setShootNum(3);
        heroAircraft.setStrategy(new ScatterShoot());

        BaseGame.pool.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                if(lastActivationTime == activationTime){
                    heroAircraft.setStrategy(new DirectShoot());
                    heroAircraft.setShootNum(1);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }
}
