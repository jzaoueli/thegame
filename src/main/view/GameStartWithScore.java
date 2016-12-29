package main.view;

import main.model.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static main.gamerunner.Runner.*;

public class GameStartWithScore extends JFrame implements ActionListener {
    /**
     * Background
     */

    private static String startBackGroundFile = "images/gamelogo.jpg";
    private BufferedImage bgImage = ImageIO.read(new File(startBackGroundFile));

    private GamePanel gameStartPanel = new GamePanel(bgImage);

    public GameStartWithScore() throws IOException, InterruptedException {
        setTitle("InfiniteFight");
        setMinimumSize(new Dimension(384, 480));
        setMaximumSize(new Dimension(384, 480));
        setLocationRelativeTo(null);

        gameStartPanel.startButton.addActionListener(this);
        gameStartPanel.highScoreButton.addActionListener(this);


        this.add(gameStartPanel);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == gameStartPanel.startButton) {
            this.setVisible(false);
            this.setEnabled(false);
            this.remove(gameStartPanel);

            try {
                setBackGround();
                setPlayer();
                setBullet();
                setEnemy();
                setItem();
                game = new Game(player, bulletClass, enemyClass, itemClass);
                gamePanel = new GamePanel(backgroundImage, game);
                gameGUI = new GameGUI(gamePanel);
                gameGUI.setVisible(true);
                game.play(gameGUI);
            } catch (IOException | InterruptedException e1) {
                e1.printStackTrace();
            }
        } else if (e.getSource() == gameStartPanel.highScoreButton) {
            // TODO:  Hier we can read a file.txt compare the value inside and overwrite it (when we have new high score)
            int highScore = 111;
            JOptionPane.showMessageDialog(null, "The High Score is: " + highScore);
        }
    }


}