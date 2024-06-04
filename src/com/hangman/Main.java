package com.hangman;

import com.hangman.assets.Drawing;
import com.hangman.panels.Guesser;
import com.hangman.panels.Hangman;
import com.hangman.panels.StartScreen;

import javax.swing.*;
import java.awt.*;

public class Main {

    // start screen variable is set here, so it can be modified in the consumer
    private static StartScreen startScreen = null;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Hangman");

        Hangman hangman = new Hangman(300, 400);
        frame.add(hangman, BorderLayout.WEST);

        // add the start screen to the window, which will collect the input string
        // and run the consumer with it as input
        startScreen = new StartScreen(300, 400, string -> {
            // replace the start screen with the guesser to start the game
            frame.remove(startScreen);
            Guesser guesser = new Guesser(300, 400, string.toLowerCase(), hangman);
            frame.add(guesser, BorderLayout.EAST);

            // focus on the start screen so it receives key events
            guesser.revalidate();
            guesser.requestFocusInWindow();
        });
        frame.add(startScreen, BorderLayout.EAST);
//        frame.add(new Drawing(40, 40), BorderLayout.EAST);

        frame.setSize(600, 400);
        frame.setVisible(true);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }

}
