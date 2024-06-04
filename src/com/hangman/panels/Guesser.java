package com.hangman.panels;

import com.hangman.assets.Drawing;
import com.hangman.assets.Text;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Guesser extends Canvas implements KeyListener {

    private final String string;
    // hangman class to receive events (e.g. the body part has been drawn)
    private final Hangman hangman;

    private final boolean[] guessed = new boolean[26];
    private final StringBuilder wrongString = new StringBuilder();

    private char guessing = 0;

    public Guesser(int width, int height, String string, Hangman hangman) {
        setSize(width, height);
        setVisible(true);

        this.string = string;
        this.hangman = hangman;

        addKeyListener(this);
    }

    public void paint(Graphics g) {
        // if the game is over, draw the correct text is gray
        if(hangman.isGameOver()) {
            g.setColor(Color.gray);
            Text.drawText(10, 10, .8, g, string, getWidth() - 20);
        } else {
            // create a string that changed any not-guessed letters to underscores
            // in the string
            StringBuilder builder = new StringBuilder();
            boolean allCorrect = true;

            for (char c : string.toCharArray()) {
                if (c < 'a' || c > 'z' || guessed[c - 'a']) {
                    builder.append(c);
                    continue;
                }

                allCorrect = false;
                builder.append('_');
            }

            Text.drawText(10, 10, .8, g, builder.toString(), getWidth() - 20);

            // if all of the characters were correct, display "You Win" on the screen
            // and stop the key listener to end the game
            if(allCorrect) {
                g.setColor(new Color(0, 150, 0));
                Text.drawText(10, getHeight() - 100, .8, g, "You Win", getWidth() - 20);
                removeKeyListener(this);
                return;
            }

            // display the character that the player is trying to guess
            if (guessing != 0) {
                // if the character is not alphabetic or it has already been guessed,
                // set the color to red to indicate that
                if (guessing < 'a' || guessing > 'z' || guessed[guessing - 'a']) {
                    g.setColor(Color.red);
                }
                Text.drawText(10, getHeight() - 100, 1, g, String.valueOf(guessing), getWidth() - 20);
            }
        }

        // draw all of the guesses that were wrong on the bottom
        g.setColor(Color.red);
        Text.drawText(10, getHeight() - 50, .5, g, wrongString.toString(), getWidth() - 20);
        g.setColor(Color.black);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // if enter is pressed, the player wants to guess the character that they had
        // previously typed (stored in `guessing`)
        if(e.getExtendedKeyCode() == KeyEvent.VK_ENTER) {
            if(guessing >= 'a' && guessing <= 'z' && !guessed[guessing - 'a']) {
                // if the guessed character is alphabetic and not already guessed
                // add it to the already guessed set and check if it is in the string
                guessed[guessing - 'a'] = true;

                // if it is not in the string, add it to the display at the bottom that
                // shows all incorrect guesses and tell the hangman object to draw the
                // next body part
                if(string.indexOf(guessing) == -1) {
                    wrongString.append(guessing);

                    // removes to key listener so the player cannot continue to guess as
                    // the body part is being drawn
                    removeKeyListener(this);
                    hangman.nextPart(() -> {
                        // callback after the body part is drawn

                        // if the game is still running, refocus the panel to accept key events
                        // and re-initialize the key listener
                        if(!hangman.isGameOver()) {
                            addKeyListener(this);
                            requestFocusInWindow();
                        }
                        repaint();
                    });
                }
            }

            // reset the guessing display character
            guessing = 0;
            repaint();
            return;
        }

        // set guessing to the current character typed, so it can be displayed
        guessing = e.getKeyChar();

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
