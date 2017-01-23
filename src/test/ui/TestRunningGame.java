package test.ui;

import main.model.Game;
import main.view.GameGUI;
import main.view.GamePanel;
import main.view.GameStartWithScore;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

import static java.lang.Thread.sleep;
import static main.gamerunner.Runner.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Game Ui test
 * running the game and assert it stay running until to reach @DISTANCE_TO_TEST
 */
public class TestRunningGame {
    private static final int DISTANCE_TO_TEST = 10;
    private static final int PLAYER_LIFE_POINT = 2000;

    private static final int RIGHT_BORDER = 320;
    private static final int LEFT_BORDER = 32;

    private JFrame frame;

    @Before
    public void setUp() throws IOException {
        givenGameObjects();
    }

    @Test
    public void testRunGame() throws IOException, InterruptedException {
        whenStartGame();
        thenGUIRunning();

        while (gamePanel.distanceValue < DISTANCE_TO_TEST) {
            givenPlayerLifePoints(PLAYER_LIFE_POINT);
            givenMovePlayer("right");

            thenGUIRunning();

            givenMovePlayer("left");

            thenGUIRunning();
        }

        //Testing Start Screen After testing game
        assertNull(frame);
        whenStartScreen();
        assertNotNull(frame);
    }

    private void whenStartScreen() throws InterruptedException, IOException {
        frame = new GameStartWithScore();
        frame.setVisible(true);
        sleep(2000);
    }

    private void setBackground() throws IOException {
        File backgroundImageFile = new File("src/test/assets/back.jpg");
        backgroundImage = ImageIO.read(backgroundImageFile);
    }

    private void whenStartGame() throws IOException, InterruptedException {
        game = new Game(player, bulletClass, enemyClass, itemClass);
        gamePanel = new GamePanel(backgroundImage, game);
        gameGUI = new GameGUI(gamePanel);
        game.play(gameGUI);
    }

    private void givenMovePlayer(String direction) throws InterruptedException {
        if (direction.equals("right")) {
            while (player.getX() < RIGHT_BORDER) {
                player.setMovement(direction);
                sleep(50);
            }
        }
        if (direction.equals("left")) {
            while (player.getX() > LEFT_BORDER) {
                player.setMovement(direction);
                sleep(50);
            }
        }
    }

    private void givenPlayerLifePoints(int lifePoints) {
        player.setLifePoints(lifePoints);
    }

    private void thenGUIRunning() {
        assertTrue(gameGUI.isEnabled());
    }

    private void givenGameObjects() throws IOException {
        setBackground();
        setPlayer();
        setBullet();
        setEnemy();
        setItem();
    }
}
