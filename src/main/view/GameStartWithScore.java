package main.view;

import main.gamerunner.Runner;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

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
                Runtime.getRuntime().exit(0);
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
                Runner.starGame();
            } catch (IOException | InterruptedException e1) {
                e1.printStackTrace();
            }
        } else if (e.getSource() == gameStartPanel.highScoreButton) {
            int highScore = 0;
            try {
                highScore = Integer.parseInt(getHighScore());
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            JOptionPane.showMessageDialog(null, "The High Score is: " + highScore);
        }
    }

    public static String getHighScore() throws FileNotFoundException {
        return new Scanner(new File("assets/score.txt")).useDelimiter("\\Z").next();
    }

}