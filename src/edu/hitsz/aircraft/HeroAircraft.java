package edu.hitsz.aircraft;

import edu.hitsz.addFunction.Shield;
import edu.hitsz.application.game.BaseGame;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.strategy.DirectShoot;

/**
 * Hero aircraft controlled by the game player.
 */
public class HeroAircraft extends AbstractAircraft {
    /**
     * Singleton pattern with double-checked locking.
     */
    private volatile static HeroAircraft heroAircraft;

    /**
     * MP of the hero aircraft (shield skill is activated when MP reaches 100).
     */
    private int mp;

    /**
     * Shield of the hero aircraft.
     */
    public Shield shield = new Shield(locationX, locationY);

    /**
     * Singleton pattern, private constructor.
     * @param locationX X coordinate of the hero aircraft.
     * @param locationY Y coordinate of the hero aircraft.
     * @param speedX Base speed of bullets fired by the hero (Hero aircraft itself has no specific speed).
     * @param speedY Base speed of bullets fired by the hero (Hero aircraft itself has no specific speed).
     * @param hp Initial health points.
     */
    private HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.shootNum = 1;
        this.power = BaseGame.heroBulletPower;
        this.direction = -1;
    }

    /**
     * Singleton pattern, provides a global access point to the only HeroAircraft instance.
     * @return the singleton instance of HeroAircraft
     */
    public static HeroAircraft getInstance() {
        if (heroAircraft == null) {
            synchronized (HeroAircraft.class) {
                if (heroAircraft == null) {
                    heroAircraft = new HeroAircraft(Main.WINDOW_WIDTH / 2,
                                                    Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight(),
                                                    0, 0, BaseGame.HERO_HP);
                    // Utilizing the Strategy pattern, initialize hero aircraft shooting strategy to direct shooting.
                    heroAircraft.setStrategy(new DirectShoot());
                }
            }
        }
        return heroAircraft;
    }


    @Override
    public void forward() {
        // Hero aircraft is controlled by the mouse, does not move via the forward method.
    }

    /**
     * Checks the state of the hero aircraft's shield.
     */
    public void shieldCheck(){
        if(this.mp >= 100 && shield.notValid()){
            this.mp = 0;
            shield.setValid(true);
            shield.setHp(100);
        }
        if(shield.getHp() <= 0){
            shield.vanish();
        }
    }

    public void setMP(int MP) {
        this.mp = MP;
    }

    public int getMP() {
        return mp;
    }
}
