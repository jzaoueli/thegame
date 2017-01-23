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

import static javax.swing.JOptionPane.showMessageDialog;

public class GameStartWithScore extends JFrame implements ActionListener {

    private static String startBackGroundFile = "images/naruto3.png";
    private BufferedImage bgImage = ImageIO.read(new File(startBackGroundFile));

    private GamePanel gameStartPanel = new GamePanel(bgImage);

    public GameStartWithScore() throws IOException, InterruptedException {
        setTitle("Naruto Fight");
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

    public void paint(Graphics g) {
        super.paint(g);

        g.drawImage(bgImage, 0, 0, null);

        g.setColor(Color.black);
        g.setFont(new Font("SansSerif", Font.CENTER_BASELINE, 50));
        g.drawString("Naruto Fight", 50, 60);

        g.setColor(Color.red);
        g.setFont(new Font("Serif", Font.PLAIN, 20));
        g.drawString("ARE", 15, 100);
        g.drawString("YOU", 15, 120);
        g.drawString("READY ", 15, 140);
        g.drawString("TO ", 15, 160);
        g.drawString("FIGHT ", 15, 180);
        g.drawString("? ", 15, 200);
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
            showMessageDialog(null,
                    "The High Score is: " + highScore, "high score", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    static String getHighScore() throws FileNotFoundException {
        return new Scanner(new File("assets/score.txt")).useDelimiter("\\Z").next();
    }

}