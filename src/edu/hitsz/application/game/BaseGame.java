package edu.hitsz.application.game;

import edu.hitsz.addFunction.Explode;
import edu.hitsz.addFunction.Shield;
import edu.hitsz.aircraft.*;
import edu.hitsz.application.HeroController;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.application.RankingMenu;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;

import edu.hitsz.dao.Dao;
import edu.hitsz.dao.DaoImplement;
import edu.hitsz.dao.Round;
import edu.hitsz.factory.BossEnemyFactory;
import edu.hitsz.factory.EliteEnemyFactory;
import edu.hitsz.factory.EnemyFactory;
import edu.hitsz.factory.MobEnemyFactory;
import edu.hitsz.music.MusicThread;
import edu.hitsz.property.AbstractProperty;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;
public abstract class BaseGame extends JPanel {
    public static boolean pause = false;
    public static final int MOB_HP_BASE = 10;
    public static final int ELITE_HP_BASE = 20;
    public static final int HERO_HP = 500;
    public static final int BOSS_HP_BASE = 500;
    public static int eliteHp = ELITE_HP_BASE;
    public static int bossHp = BOSS_HP_BASE;
    public static final int SHIELD_HP = 100;
    public static final int MOB_SCORE_UP = 10;
    public static final int ELITE_SCORE_UP = 20;
    public static final int BOSS_SCORE_UP = 50;
    public static int enemyBulletPower = 30;
    public static int heroBulletPower = 30;
    public static final int MOB_SPEED_X = 0;
    public static final int MOB_SPEED_Y = 10;
    public static final int BOSS_SPEED_X = 3;
    public static final int BOSS_SPEED_Y = 0;
    public static final int ELITE_SPEED_Y_BASE = 8;
    public static int eliteSpeedY = ELITE_SPEED_Y_BASE;
    public static final int EASY = 0;
    public static final int NORMAL = 1;
    public static final int HARD = 2;
    public static int gameDifficulty;
    private float elitePossibility = 0.5F;
    private int bossScoreThreshold = 150;
    private int thresholdScore;
    private double rate = 1;
    private double rateUp = 0;
    private int cycleDurationDecrease = 5;
    protected int enemyMaxNumber = 5;
    protected Color bossBarColor;
    public static double propertyDropPossibility = 0.3;
    private final List<AbstractEnemyAircraft> enemyAircrafts;
    private final List<BaseBullet> heroBullets;
    private final List<BaseBullet> enemyBullets;
    private final List<AbstractProperty> properties;
    private final List<Explode> explodeList;
    private final HeroAircraft heroAircraft;
    private static BossEnemy boss;
    private boolean bossExists = false;
    public static Dao dao;
    private File dataFilePath;
    private final ScheduledExecutorService executorService;
    public static ExecutorService pool = Executors.newCachedThreadPool();
    public static boolean musicOn = true;
    private MusicThread bgm = null;
    private MusicThread bossBgm = null;
    private int time = 0;
    private int score = 0;
    private int timeInterval = 40;
    private int cycleDuration = 600;
    private int cycleTime = 0;
    private boolean gameOverFlag = false;
    private int backGroundTop = 0;
    private EnemyFactory enemyFactory;
    public abstract void initial();
    public abstract boolean needBossEnemy();
    public BaseGame() {
        initial();
        heroAircraft = HeroAircraft.getInstance();
        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        properties = new LinkedList<>();
        explodeList = new LinkedList<>();
        this.executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("baseGame-action-%d").daemon(true).build());
        new HeroController(this, heroAircraft);
    }

    public void action() {
        Runnable task = this::performGameLoop;
        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);
    }
    private void performGameLoop() {
        controlBackgroundMusic();
        checkGamePauseStatus();
        updateTimeAndDifficulty();
        maybeSpawnEnemiesAndShoot();
        maybeControlBoss();
        moveBullets();
        moveAircrafts();
        checkCollisions();
        checkHeroShield();
        cleanupGameObjects();
        repaint();
        checkGameOver();
    }
    private void controlBackgroundMusic() {
        musicBgmControl();
    }
    private void checkGamePauseStatus() {
        gamePauseCheck();
    }
    private void updateTimeAndDifficulty() {
        time += timeInterval;
        increaseGameDifficulty();
    }
    private void maybeSpawnEnemiesAndShoot() {
        if (timeCountAndNewCycleJudge()) {
            System.out.println(time);
            spawnEnemies();
            shootAction();
        }
    }
    private void spawnEnemies() {
        if (enemyAircrafts.size() < enemyMaxNumber) {
            enemyFactory = (new Random().nextFloat() < elitePossibility) ? new EliteEnemyFactory() : new MobEnemyFactory();
            enemyAircrafts.add(enemyFactory.createEnemy());
        }
    }
    private void maybeControlBoss() {
        if (needBossEnemy()) {
            bossControl();
        }
    }
    private void moveBullets() {
        bulletsMoveAction();
    }
    private void moveAircrafts() {
        aircraftsMoveAction();
    }
    private void checkCollisions() {
        crashCheckAction();
    }
    private void checkHeroShield() {
        heroAircraft.shieldCheck();
    }
    private void cleanupGameObjects() {
        postProcessAction();
    }
    private void repaintGame() {
        repaint();
    }
    private void checkGameOver() {
        checkHeroAlive();
    }
    private void musicBgmControl() {
        if (!musicOn) {
            return;
        }
        if (bossExists) {
            if (bossBgm == null || !bossBgm.isAlive()) {
                if (bgm != null) {
                    bgm.stopMusic();
                    bgm = null;
                }
                bossBgm = new MusicThread("src/videos/bgm_boss.wav");
                bossBgm.start();
            }
        } else {
            if (bgm == null || !bgm.isAlive()) {
                if (bossBgm != null) {
                    bossBgm.stopMusic();
                    bossBgm = null;
                }
                bgm = new MusicThread("src/videos/bgm.wav");
                bgm.start();
            }
        }
    }
    private void gamePauseCheck() {
        while (pause) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (bossBgm != null) {
                bossBgm.stopMusic();
            }
            if (bgm != null) {
                bgm.stopMusic();
            }
            repaint();
        }
    }

    private boolean timeCountAndNewCycleJudge() {
        cycleTime += timeInterval;

        if (cycleTime >= cycleDuration) {
            cycleTime %= cycleDuration;  
            return true;  
        }

        return false;  
    }

    private static final int TIME_THRESHOLD = 6000;
    private static final int MIN_CYCLE_DURATION = 300;

    private void increaseGameDifficulty() {
        if (time % TIME_THRESHOLD == 0) {
            updateAircraftAttributes();
            updateCycleDuration();
        }
    }

    private void updateAircraftAttributes() {
        if (rateUp != 0) {
            rate += rateUp;
            System.out.printf("Elite and BOSS aircraft health multiplier: %.2f, Elite aircraft speed multiplier: %.2f%n", rate, (rate + 1) / 2);
            bossHp = (int) (BOSS_HP_BASE * rate);
            eliteHp = (int) (ELITE_HP_BASE * rate);
            eliteSpeedY = (int) (ELITE_SPEED_Y_BASE * (rate + 1) / 2);
        }
    }

    private void updateCycleDuration() {
        if (cycleDurationDecrease != 0) {
            if (cycleDuration > MIN_CYCLE_DURATION) {
                cycleDuration -= cycleDurationDecrease;
                System.out.println("The time cycle cycleDuration has been reduced to " + cycleDuration / 600.0 + "! The enemy spawn rate and the firing rate of all aircraft have increased!");
            } else {
                System.out.println("The time cycle cycleDuration has been halved, and is now reduced to the shortest!");
            }
        }
    }

    private void shootAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            List<BaseBullet> enemyBulletList = enemyAircraft.shoot(enemyAircraft);
            enemyBullets.addAll(enemyBulletList);
        }
        heroBullets.addAll(heroAircraft.shoot(heroAircraft));
    }
    private void bossControl() {
        manageBossSpawn();
        updateBossStatus();
    }

    private void manageBossSpawn() {
        if (score >= thresholdScore) {
            thresholdScore += bossScoreThreshold;
            if (!bossExists) {
                spawnBoss();
            }
        }
    }

    private void spawnBoss() {
        enemyFactory = new BossEnemyFactory();
        BossEnemy bossEnemy = (BossEnemy) enemyFactory.createEnemy();
        enemyAircrafts.add(bossEnemy);
        boss = bossEnemy;
    }

    private void updateBossStatus() {
        if (boss != null) {
            bossExists = boss.getHp() > 0;
        }
    }

    private void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }
    private void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
        for (AbstractProperty propertyAircraft : properties) {
            propertyAircraft.forward();
        }
    }

    private void crashCheckAction() {

        for (BaseBullet bullet : enemyBullets) {
            if (bullet.notValid() || !heroAircraft.crash(bullet)) continue;

            if (musicOn) {
                pool.execute(new MusicThread("src/videos/bullet_hit.wav"));
            }
            if (!heroAircraft.shield.notValid()) {
                heroAircraft.shield.decreaseHp(bullet.getPower());
            } else {
                heroAircraft.decreaseHp(bullet.getPower());
            }
            bullet.vanish();
        }


        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) continue;

            for (AbstractEnemyAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid() || !enemyAircraft.crash(bullet)) continue;

                if (musicOn) {
                    pool.execute(new MusicThread("src/videos/bullet_hit.wav"));
                }
                enemyAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
                if (enemyAircraft.notValid()) {
                    explodeList.add(new Explode(enemyAircraft));
                    enemyAircraft.dropProperty(properties, enemyAircraft.getLocationX(), enemyAircraft.getLocationY());
                    if (heroAircraft.shield.notValid()) {
                        heroAircraft.setMP(heroAircraft.getMP() + 10);
                    }
                }
            }
        }

        for (AbstractEnemyAircraft enemy : enemyAircrafts) {
            if (enemy.notValid() || !(enemy.crash(heroAircraft) || heroAircraft.crash(enemy))) continue;
            enemy.vanish();
            heroAircraft.decreaseHp(Integer.MAX_VALUE);
        }

        for (AbstractProperty property : properties) {
            if (property.notValid() || !(heroAircraft.crash(property) || property.crash(heroAircraft))) continue;
            property.propertyActive(heroAircraft);
            property.vanish();
        }
    }


    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        properties.removeIf(AbstractFlyingObject::notValid);
        explodeList.removeIf(Explode::explodeEnd);
    }

    private void checkHeroAlive() {
        if (heroAircraft.getHp() <= 0) {
            handleGameOver();
        }
    }

    private void handleGameOver() {

        stopGameMusic();

        gameOverFlag = true;

        executorService.shutdown();

        printRankingList();
        System.out.println("Base Game Over!");
    }

    private void stopGameMusic() {
        if (musicOn) {
            try {
                pool.execute(new MusicThread("src/videos/game_over.wav"));
                if (bgm != null) {
                    bgm.stopMusic();
                }
                if (bossBgm != null) {
                    bossBgm.stopMusic();
                }
            } catch (Exception e) {
                System.out.println("Error stopping music: " + e.getMessage());
            }
        }
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        paintBackground(g);
        paintAllFlyingObjects(g);
        paintHero(g);
        paintEffects(g);
        paintUIElements(g);
    }

    private void paintBackground(Graphics g) {
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop, null);
        updateBackgroundScroll();
    }

    private void updateBackgroundScroll() {
        if (!BaseGame.pause) {
            this.backGroundTop += 1;
            if (this.backGroundTop == Main.WINDOW_HEIGHT) {
                this.backGroundTop = 0;
            }
        }
    }

    private void paintAllFlyingObjects(Graphics g) {
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);
        paintImageWithPositionRevised(g, enemyAircrafts);
        paintImageWithPositionRevised(g, properties);
    }

    private void paintHero(Graphics g) {
        g.drawImage(ImageManager.HERO_IMAGE, 
            heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
            heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);
    }

    private void paintEffects(Graphics g) {
        paintImagesExplosion(g);
    }

    private void paintUIElements(Graphics g) {
        if (bossExists) {
            paintBossLifeBar(g);
        }
        paintHeroMPBar(g);
        paintShield(g);
        paintScoreAndLife(g);
        paintPauseIfNeeded(g);
    }

    private void paintPauseIfNeeded(Graphics g) {
        if (BaseGame.pause) {
            paintPause(g);
        }
    }

    private void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.isEmpty()) {
            return; 
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            if (image == null) {
                System.err.println(objects.getClass().getName() + " has no image!");
                continue; 
            }
            int imageWidth = image.getWidth();
            int imageHeight = image.getHeight();
            int x = object.getLocationX() - imageWidth / 2;
            int y = object.getLocationY() - imageHeight / 2;

            g.drawImage(image, x, y, null); 
        }
    }


    private void paintImagesExplosion(Graphics g) {
        Iterator<Explode> iterator = explodeList.iterator();
        while (iterator.hasNext()) {
            Explode explode = iterator.next();
            if (explode.getExplodeCount() < 16) {
                explode.draw(g);
            } else {
                iterator.remove(); 
            }
        }
    }

    private void paintBossLifeBar(Graphics g) {
        if (boss == null || boss.getHp() <= 0) {
            return; 
        }

        final int barWidth = 100; 
        final int barHeight = 6; 
        final int barX = 30; 
        final int barY = 63; 

        Graphics2D g2 = (Graphics2D) g;
        GradientPaint paint = new GradientPaint(1, 1, Color.white, barWidth, barHeight, bossBarColor, true);
        g2.setPaint(paint);
        int lifeBarLength = barWidth * boss.getHp() / bossHp; 
        g2.fillRect(barX, barY, lifeBarLength, barHeight);

        g.drawImage(ImageManager.BLOOD_BAR_IMAGE, 5, 50, null);
    }

    private void paintHeroMPBar(Graphics g) {
        final int mpBarWidth = 100; 
        final int mpBarHeight = 10; 
        final int mpBarX = Main.WINDOW_WIDTH - 120; 
        final int mpBarY = 20; 

        g.setColor(Color.white);
        g.fillRect(mpBarX, mpBarY, mpBarWidth, mpBarHeight);

        int currentMP = Math.min(heroAircraft.getMP(), mpBarWidth);
        g.setColor(Color.blue);
        g.fillRect(mpBarX, mpBarY, currentMP, mpBarHeight);
    }

    private void paintShield(Graphics g) {
        if (!heroAircraft.shield.notValid()) {
            BufferedImage image = ImageManager.get(Shield.class.getName());
            g.drawImage(image, heroAircraft.getLocationX() - image.getWidth() / 2, heroAircraft.getLocationY() - image.getHeight() / 2, null);
        }
    }

    private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;

        g.setColor(Color.red); 
        g.setFont(new Font("SansSerif", Font.BOLD, 22));

        g.drawString("SCORE: " + this.score, x, y);
        y += 20; 
        g.drawString("LIFE: " + this.heroAircraft.getHp(), x, y);
    }

    private void paintPause(Graphics g) {

        g.setColor(Color.red);
        g.setFont(new Font("SansSerif", Font.BOLD, 20));
        String pauseText = "PAUSE";
        int pauseTextWidth = g.getFontMetrics().stringWidth(pauseText);
        int pauseX = Main.WINDOW_WIDTH / 2 - pauseTextWidth / 2;
        int pauseY = Main.WINDOW_HEIGHT / 2 - 50;
        g.drawString(pauseText, pauseX, pauseY);

        String continueText = "Press right mouse button to continue";
        int continueTextWidth = g.getFontMetrics().stringWidth(continueText);
        int continueX = Main.WINDOW_WIDTH / 2 - continueTextWidth / 2;
        int continueY = Main.WINDOW_HEIGHT / 2 + 10;
        g.drawString(continueText, continueX, continueY);
    }


    private void printRankingList() {
        try {
            updateRankings();
            displayRankingUI();
            handleUserScoreInput();
        } catch (Exception e) {
            System.err.println("Error handling ranking list: " + e.getMessage());
        }
    }

    private void updateRankings() {
        dao = new DaoImplement(dataFilePath);
        dao.sortRanks();
    }

    private void displayRankingUI() {
        RankingMenu rankingMenu = new RankingMenu();
        Main.cardPanel.add(rankingMenu.getMainPanel());
        Main.cardLayout.next(Main.cardPanel);
    }

    private void handleUserScoreInput() {
        String promptMessage = "Game over, your score is " + score + ",\nplease enter your name to record the score:";
        String userName = JOptionPane.showInputDialog(null, promptMessage);
        if (userName != null && !userName.trim().isEmpty()) {
            processScore(userName);
            refreshRankingDisplay(); 
        }
    }

    private void processScore(String userName) {
        Round round = new Round(dao.getRoundsNum() + 1, userName, score);
        dao.addRound(round);
        dao.sortRanks();
        dao.save();
    }

    private void refreshRankingDisplay() {
        RankingMenu rankingMenu = new RankingMenu();
        rankingMenu.updateData();
        Main.cardPanel.add(rankingMenu.getMainPanel());
        Main.cardLayout.next(Main.cardPanel);
    }

 
    public List<AbstractEnemyAircraft> getEnemyAircrafts() {
        return Collections.unmodifiableList(enemyAircrafts);
    }


    public List<BaseBullet> getEnemyBullets() {
        return Collections.unmodifiableList(enemyBullets);
    }


    public static void setGameDifficulty(final int gameDifficulty) {
        BaseGame.gameDifficulty = gameDifficulty;
    }

    public static void setEnemyBulletPower(final int enemyBulletPower) {
        BaseGame.enemyBulletPower = enemyBulletPower;
    }


    public void setElitePossibility(final float elitePossibility) {
        this.elitePossibility = elitePossibility;
    }


    public void setRateUp(final double rateUp) {
        this.rateUp = rateUp;
    }


    public void setBossScoreThreshold(final int bossScoreThreshold) {
        this.bossScoreThreshold = bossScoreThreshold;
        this.thresholdScore = bossScoreThreshold; 
    }

    public void setDataFilePath(final File dataFilePath) {
        this.dataFilePath = dataFilePath;
    }

    public void addExplodeList(final AbstractAircraft aircraft) {
        explodeList.add(new Explode(aircraft));
    }

    public void increaseScore(final int number) {
        this.score += number;
    }

    public void setCycleDurationDecrease(final int number) {
        cycleDurationDecrease = number;
    }
}