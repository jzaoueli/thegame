package main.gamerunner;

import main.generated.bullet.BulletNormal;
import main.generated.enemy.*;
import main.generated.image.ImageBackground;
import main.generated.image.ImagePlayer;
import main.generated.item.BulletRateItemData;
import main.generated.item.HealItemData;
import main.model.Game;
import main.model.Player;
import main.view.GameGUI;
import main.view.GamePanel;
import main.view.GameStartWithScore;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * TODO Game Control
 * TODO Documentation
 */
public class Runner {
    private static String imageDirectory = "images/";
    public static BufferedImage backgroundImage;
    public static Player player;

    public static ArrayList<Object[]> bulletClass;
    public static ArrayList<Object[]> enemyClass;
    public static ArrayList<Object[]> itemClass;

    public static GameGUI gameGUI;
    public static GamePanel gamePanel;
    public static Game game;
    public static JFrame frame2;

    //TODO
    public static void main(String[] args) throws IOException, InterruptedException {

        frame2 = new GameStartWithScore();
        frame2.setVisible(true);
        //starGame();
    }

    public static void starGame() throws IOException, InterruptedException {
        setUpImageObject();
        game = new Game(player, bulletClass, enemyClass, itemClass);
        gamePanel = new GamePanel(backgroundImage, game);
        gameGUI = new GameGUI(gamePanel);
        game.play(gameGUI);
    }

    public static void destroyGame(){
        gameGUI.dispose();
    }

    private static void setUpImageObject() throws IOException {
        setBackGround();
        setPlayer();
        setBullet();
        setEnemy();
        setItem();
    }

    public static void setItem() {
        itemClass = new ArrayList<>();
        Object[] healItemObject = new HealItemData().getHealItemData();
        itemClass.add(healItemObject);
        Object[] bulletRateItemObject = new BulletRateItemData().getBulletRateItemData();
        itemClass.add(bulletRateItemObject);
    }

    public static void setEnemy() {
        enemyClass = new ArrayList<>();
        Object[] commonAttackEnemyObject = new CommonAttackEnemyData().getCommonAttackEnemyData();
        enemyClass.add(commonAttackEnemyObject);

        Object[] commonDefenseEnemyObject = new CommonDefenseEnemyData().getCommonDefenseEnemyData();
        enemyClass.add(commonDefenseEnemyObject);

        Object[] uncommonAttackEnemyObject = new UncommonAttackEnemyData().getUncommonAttackEnemyData();
        enemyClass.add(uncommonAttackEnemyObject);

        Object[] uncommonDefenseEnemyObject = new UncommonDefenseEnemyData().getUncommonDefenseEnemyData();
        enemyClass.add(uncommonDefenseEnemyObject);

        Object[] rareAttackEnemyObject = new RareAttackEnemyData().getRareAttackEnemyData();
        enemyClass.add(rareAttackEnemyObject);

        Object[] rareDefenseEnemyData = new RareDefenseEnemyData().getRareDefenseEnemyData();
        enemyClass.add(rareDefenseEnemyData);
    }

    public static void setBullet() {
        bulletClass = new ArrayList<>();
        Object[] bulletObject = new BulletNormal().getBulletImage();
        bulletClass.add(bulletObject);
    }

    public static void setPlayer() throws IOException {
        Object[] playerObject = new ImagePlayer().getPlayerImage();
        player = new Player(playerObject);
    }

    public static void setBackGround() throws IOException {
        String backgroundImageFileName = new ImageBackground().getFileName();
        File backgroundImageFile = new File(imageDirectory + backgroundImageFileName);
        backgroundImage = ImageIO.read(backgroundImageFile);
    }
}
