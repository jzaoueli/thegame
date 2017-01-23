package main.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.MemoryImageSource;
import java.io.IOException;

import java.io.*;
import javax.sound.sampled.*;


public class GameGUI extends JFrame {

    /**
     * Sound parameters
     */
    static AudioFormat audioFormat;
    static AudioInputStream audioInputStream;
    static SourceDataLine sourceDataLine;
    /*
    End Sound parameters
    */

    public GameGUI(GamePanel gamePanel) throws IOException, InterruptedException {

        setTitle("Naruto Fight");
        setSize(new Dimension(400, 500));
        setResizable(false);
        setLocationRelativeTo(null);

        this.add(gamePanel);
        gamePanel.setVisible(true);

        int[] pixels = new int[16 * 16];
        Image image = Toolkit.getDefaultToolkit().createImage(
                new MemoryImageSource(16, 16, pixels, 0, 16));
        Cursor transparentCursor =
                Toolkit.getDefaultToolkit().createCustomCursor
                        (image, new Point(0, 0), "invisibleCursor");
        this.setCursor(transparentCursor);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                Runtime.getRuntime().exit(0);
            }
        });

        playAudio();//Play the file

    }


    private void playAudio() {
        try {

            String soundPath = "sound/battle.au";
            File soundFile =
                    new File(soundPath);
            audioInputStream = AudioSystem.
                    getAudioInputStream(soundFile);
            audioFormat = audioInputStream.getFormat();

            DataLine.Info dataLineInfo =
                    new DataLine.Info(
                            SourceDataLine.class,
                            audioFormat);

            sourceDataLine =
                    (SourceDataLine) AudioSystem.getLine(
                            dataLineInfo);

            //Create a thread to play back the data and
            // start it running.  It will run until the
            // end of file

            new PlayThread().start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }//end catch
    }

    static void stopAudio() {
        sourceDataLine.close();
    }

    //Inner class to play back the data from the audio file.
    static class PlayThread extends Thread {
        byte tempBuffer[] = new byte[10000];

        public void run() {
            try {
                sourceDataLine.open(audioFormat);
                sourceDataLine.start();

                int cnt;
                //Keep looping until the input read method
                // returns -1 for empty stream
                // while((cnt = audioInputStream.read(tempBuffer,0,tempBuffer.length)) != -1 )

                while ((cnt = audioInputStream.read(tempBuffer, 0, tempBuffer.length)) != -1) {
                    if (cnt > 0) {
                        //Write data to the internal buffer of
                        // the data line where it will be
                        // delivered to the speaker.
                        sourceDataLine.write(
                                tempBuffer, 0, cnt);
                    }
                }//end while
                //Block and wait for internal buffer of the
                // data line to empty.
                sourceDataLine.drain();
                sourceDataLine.close();

            } catch (Exception e) {
                e.printStackTrace();
                System.exit(0);
            }//end catch
        }//end run
    }


}