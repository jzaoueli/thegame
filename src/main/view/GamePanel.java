package main.view;

import main.gamerunner.Runner;
import main.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class GamePanel extends JPanel implements KeyListener {

    // button to begin the Game
    JButton startButton = new JButton("Start");
    // button to show the high Score
    JButton highScoreButton = new JButton("High Score");


    public Player player;

    /**
     * The actual game
     */
    private Game game;
    private ArrayList<Bullet> onScreenBullet;
    private ArrayList<Enemy> aliveEnemy;
    private ArrayList<Item> leftItem;
    /**
     * BACKGROUND
     * Background Image
     */
    private BufferedImage backgroundImage;
    /**
     * Background Image Copy for seamless Scrolling
     */
    private BufferedImage backgroundImageOff;
    /**
     * Background Image Y Position
     */
    private int backgroundY;
    /**
     * Background Image Copy Y Position
     */
    private int backgroundOffY;
    /**
     * somme of frame shown, +1 in every repaint()
     */
    private int stateGame = 0;

    /**
     * Player movement direction
     */
    private String direction = "";

    /**
     * display  SCORE, DISTANCE and BonusLives
     */
    private int scoreValue;
    public int distanceValue;
    private int playerLifeValue;
    private String gameInfo = "";
    private String hintRestart = "";
    private String hintExit = "";
    private Font dataFont = new Font("Monospaced", Font.PLAIN, 20);
    private Font infoFont = new Font("Monospaced", Font.PLAIN, 60);

    private boolean isGameOver = false;
    /**
     * GUI Timer
     * Animates Objects on Screen
     */
    private Timer guiTimer = new Timer(32, e -> {
        /*
         * Animate Background seamlessly
         */
        backgroundY++;
        backgroundOffY = backgroundY - backgroundImage.getHeight();
        if (backgroundY == backgroundImage.getHeight()) {
            backgroundY = 0;
        }

        /*
         * Animate Player
         * Move
         */
        player.setMovement(direction);
        player.getPlayerAnimation().animate();
        player.getPlayerAnimation().setActionFrames(0);
        playerLifeValue = player.getLifePoints();

        /*
          When Player dies Game Over
         */
        if (player.getLifePoints() <= 0) {
            try {
                int oldHighScore = Integer.parseInt(GameStartWithScore.getHighScore());
                if (oldHighScore < scoreValue) {
                    setHighScore(scoreValue);
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            isGameOver = true;
            gameInfo = "GAME OVER";
            hintRestart = "press ENTER to replay,";
            hintExit = "or SPACE to go to start screen";
            playerLifeValue = 0;
            player.getPlayerAnimation().setActionFrames(2);
            player.setMovement("down");
            this.game.shootTimer.stop();
            this.game.enemyTimer.stop();
        }

        /*
          Animate Bullets
          Move Bullets
         */
        for (Bullet bullet : onScreenBullet) {
            bullet.setMovement();
            bullet.getBulletAnimation().animate();
        }

        /*
          Animate Enemies
          Move Enemies
         */
        for (Enemy enemy : aliveEnemy) {
            for (Bullet bullet : onScreenBullet) {
                if (bullet.getBoundingBox().intersects(enemy.getBoundingBox())) {
                    scoreValue += bullet.getAttack();
                    enemy.enemyAnimation.setActionFrames(1);
                    enemy.setDefense(enemy.getDefense() - bullet.getAttack());
                    if (enemy.getDefense() == 0) {
                        scoreValue += enemy.getAttack();
                        enemy.enemyAnimation.setActionFrames(2);
                        break;
                    }
                    bullet.setY(0);
                }
            }

            if (player.getBoundingBox().intersects(enemy.getBoundingBox())) {
                player.getPlayerAnimation().setActionFrames(1);
                player.setLifePoints(player.getLifePoints() - enemy.getAttack());
                enemy.setDefense(0);
            }

            enemy.setMovement(enemy.movement);
            enemy.enemyAnimation.animate();
            enemy.enemyAnimation.setActionFrames(0);
        }

        /*
         * Animate Items
         * Move Items
         */

        for (Item item : leftItem) {
            if (item.getBoundingBox().intersects(player.getBoundingBox())) {
                item.isEffectActivated = true;
            }
            item.setMovement(1);
            item.getItemAnimation().animate();
        }

        repaint();
    });

    private void setHighScore(int score) throws IOException {
        File file = new File("assets/score.txt");
        FileWriter writer = new FileWriter(file);
        writer.write("" + score);
        writer.flush();
        writer.close();
    }


    // added constructor without params
    GamePanel(BufferedImage backgroundImage) throws IOException {
        this.setLayout(null);
        this.backgroundImage = backgroundImage;
        this.backgroundImageOff = backgroundImage;
        this.backgroundY = 0;
        this.highScoreButton.setBounds(200, 350, 100, 40);
        this.add(highScoreButton);
        this.startButton.setBounds(80, 350, 100, 40);
        this.add(startButton);
    }

    public GamePanel(BufferedImage backgroundImage, Game game) throws IOException {

        this.backgroundImage = backgroundImage;
        this.backgroundImageOff = backgroundImage;
        this.backgroundY = 0;

        this.game = game;

        this.onScreenBullet = this.game.onScreenBullet;
        this.aliveEnemy = this.game.aliveEnemy;
        this.leftItem = this.game.leftItem;
        this.player = this.game.player;

        this.guiTimer.start();

        this.setFocusable(true);
        addKeyListener(this);
    }

    public void paint(Graphics g) {
        super.paint(g);

        /*
         * Increase game speed over time
         */
        stateGame += 1;

        if ((stateGame % 30) == 0) {
            if (!isGameOver) {
                distanceValue += 1;
            }
            if ((distanceValue >= 100) && (distanceValue % 100) == 0 && game.enemyTimer.getDelay() > 100) {
                game.enemyTimer.setDelay(game.enemyTimer.getDelay() - 20);
            }
            if ((distanceValue >= 300) && (distanceValue % 300) == 0 && guiTimer.getDelay() > 1) {
                guiTimer.setDelay(guiTimer.getDelay() - 1);
            }
        }


        if(this.game != null){
            /*
             * Draw Background seamlessly
             */
            g.drawImage(backgroundImage, 0, backgroundY, null);
            g.drawImage(backgroundImageOff, 0, backgroundOffY, null);
            /*
             * Draw Player
             */
            g.drawImage(this.player.playerAnimation.frame, player.getX(), player.getY(), null);
            /*
             * Draw Bullets
             */
            for (Bullet bullet : onScreenBullet) {
                g.drawImage(bullet.bulletAnimation.frame, bullet.getX(), bullet.getY(), null);
            }
            /*
             * Draw Enemies
             */
            for (Enemy enemy : aliveEnemy) {
                g.drawImage(enemy.enemyAnimation.frame, enemy.getX(), enemy.getY(), null);
            }

            /*
             * Draw Items
             */
            for (Item item : leftItem) {
                g.drawImage(item.itemAnimation.frame, item.getX(), item.getY(), null);
            }
            g.setColor(Color.black);
            g.setFont(dataFont);
            g.drawString(("SCORE: " + scoreValue), 15, 25);
            g.drawString(("DISTANCE: " + distanceValue), 15, 45);
            g.drawString(("LIFE: " + playerLifeValue), 15, 65);
            g.drawString(hintRestart, 20, 340);
            g.drawString(hintExit, 20, 380);
            g.setColor(Color.red);
            g.setFont(infoFont);
            g.drawString(gameInfo, 35, 240);
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (!isGameOver) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                direction = "left";
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                direction = "right";
            }
        }
        if (isGameOver && e.getKeyCode() == KeyEvent.VK_ENTER) {
            this.setVisible(false);
            this.setEnabled(false);
            Runner.destroyGame();
            try {
                Runner.starGame();
            } catch (IOException | InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!isGameOver) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                direction = "left";
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                direction = "right";
            }
        }
        if (isGameOver && (e.getKeyCode() == KeyEvent.VK_ENTER) || e.getKeyCode()== KeyEvent.VK_SPACE) {
            this.setVisible(false);
            this.setEnabled(false);
            Runner.destroyGame();
            GameGUI.stopAudio();
            try {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    Runner.starGame();
                }else if(e.getKeyCode()== KeyEvent.VK_SPACE){
                    Runner.main(null);
                }

            } catch (IOException | InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        direction = "";
    }
}



