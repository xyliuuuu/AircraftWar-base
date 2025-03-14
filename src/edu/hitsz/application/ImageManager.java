package edu.hitsz.application;


import edu.hitsz.addFunction.Shield;
import edu.hitsz.aircraft.*;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.property.BloodProperty;
import edu.hitsz.property.BulletProperty;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages loading and accessing images centrally.
 * Provides static methods for accessing images.
 */
public class ImageManager {

	 /**
     * Map storing images associated with class names, allowing retrieval of an image by the class name.
     * Use CLASSNAME_IMAGE_MAP.get(obj.getClass().getName()) to get the corresponding image for the object's class.
     */
    private static final Map<String, BufferedImage> CLASSNAME_IMAGE_MAP = new HashMap<>();

    public static BufferedImage BACKGROUND_IMAGE;
    public static BufferedImage HERO_IMAGE;
    public static BufferedImage HERO_BULLET_IMAGE;
    public static BufferedImage ENEMY_BULLET_IMAGE;
    public static BufferedImage SUPER_BULLET_IMAGE;
    public static BufferedImage MOB_ENEMY_IMAGE;
    public static BufferedImage BOSS_ENEMY_IMAGE;
    public static BufferedImage ELITE_ENEMY_IMAGE;
    public static BufferedImage PROP_BLOOD_IMAGE;
    public static BufferedImage PROP_BOMB_IMAGE;
    public static BufferedImage PROP_BULLET_IMAGE;
    public static BufferedImage SHIELD_IMAGE;
    public static BufferedImage BLOOD_BAR_IMAGE;
    public static BufferedImage BLOOD_BAR_IMAGE_HARD;
    public static BufferedImage BLOOD_BAR_IMAGE_NORMAL;

    public static BufferedImage[] EXPLODE_IMAGE_LIST = new BufferedImage[16];

    // Static block, runs when the class is loaded, preloading images.
    static {
        try {
            BACKGROUND_IMAGE = ImageIO.read(new FileInputStream("src/images/bg.jpg"));
            HERO_IMAGE = ImageIO.read(new FileInputStream("src/images/hero.png"));
            MOB_ENEMY_IMAGE = ImageIO.read(new FileInputStream("src/images/mob.png"));
            ELITE_ENEMY_IMAGE = ImageIO.read(new FileInputStream("src/images/elite.png"));
            BOSS_ENEMY_IMAGE = ImageIO.read(new FileInputStream("src/images/boss.png"));
            HERO_BULLET_IMAGE = ImageIO.read(new FileInputStream("src/images/bullet_hero.png"));
            ENEMY_BULLET_IMAGE = ImageIO.read(new FileInputStream("src/images/bullet_enemy.png"));          
            PROP_BLOOD_IMAGE = ImageIO.read(new FileInputStream("src/images/prop_blood.png"));
            PROP_BULLET_IMAGE = ImageIO.read(new FileInputStream("src/images/prop_bullet.png"));
            SHIELD_IMAGE = ImageIO.read(new FileInputStream("src/images/shield/sheild.png"));
            BLOOD_BAR_IMAGE_NORMAL = ImageIO.read(new FileInputStream("src/images/blood_bar_normal.png"));
            BLOOD_BAR_IMAGE_HARD = ImageIO.read(new FileInputStream("src/images/blood_bar_hard.png"));

            CLASSNAME_IMAGE_MAP.put(HeroAircraft.class.getName(), HERO_IMAGE);
            CLASSNAME_IMAGE_MAP.put(MobEnemy.class.getName(), MOB_ENEMY_IMAGE);
            CLASSNAME_IMAGE_MAP.put(EliteEnemy.class.getName(), ELITE_ENEMY_IMAGE);
            CLASSNAME_IMAGE_MAP.put(BossEnemy.class.getName(), BOSS_ENEMY_IMAGE);
            CLASSNAME_IMAGE_MAP.put(HeroBullet.class.getName(), HERO_BULLET_IMAGE);
            CLASSNAME_IMAGE_MAP.put(EnemyBullet.class.getName(), ENEMY_BULLET_IMAGE);
            CLASSNAME_IMAGE_MAP.put(BloodProperty.class.getName(), PROP_BLOOD_IMAGE);
            CLASSNAME_IMAGE_MAP.put(BulletProperty.class.getName(), PROP_BULLET_IMAGE);
            CLASSNAME_IMAGE_MAP.put(Shield.class.getName(), SHIELD_IMAGE);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static BufferedImage get(String className) {
        return CLASSNAME_IMAGE_MAP.get(className);
    }

    public static BufferedImage get(Object obj) {
        if (obj == null) {
            return null;
        }
        return get(obj.getClass().getName());
    }

    public static void set(String className, BufferedImage bufferedImage) {
        CLASSNAME_IMAGE_MAP.replace(className, bufferedImage);
    }
}
