package main.view;

import main.gamerunner.Runner;
import main.model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.io.IOException;

import static com.sun.java.accessibility.util.AWTEventMonitor.addActionListener;
import static main.gamerunner.Runner.*;

/**
 * Created by user on 25/12/2016.
 */
public class GameStartWithScore  extends JFrame implements ActionListener {


    private GamePanel gameStartPanel = new GamePanel();

    public GameStartWithScore() throws IOException, InterruptedException {
        setTitle("InfiniteFight");
        setMinimumSize(new Dimension(384, 480));
        setMaximumSize(new Dimension(384, 480));
        setLocationRelativeTo(null);


        gameStartPanel.highScoreButton.addActionListener(this);
        gameStartPanel.startButton.addActionListener(this);

        this.add(gameStartPanel);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == gameStartPanel.startButton){
            this.setVisible(false);
            this.setEnabled(false);
            this.remove(gameStartPanel);

            try {
//                game = new Game(player, bulletClass, enemyClass, itemClass);
//                gamePanel = new GamePanel(backgroundImage, game);
//                gameGUI = new GameGUI(gamePanel);
//                game.play(gameGUI);

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

            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
        else if (e.getSource() == gameStartPanel.highScoreButton){
            JOptionPane.showMessageDialog(null,"High Score");
        }
    }


}