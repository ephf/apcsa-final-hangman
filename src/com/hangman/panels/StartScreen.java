package com.hangman.panels;

import com.hangman.assets.Text;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.function.Consumer;

public class StartScreen extends Canvas implements KeyListener {

    private final Consumer<String> onSubmit;
    private final StringBuilder builder = new StringBuilder();

    public StartScreen(int width, int height, Consumer<String> onSubmit) {
        setSize(width, height);
        setVisible(true);
        this.onSubmit = onSubmit;

        addKeyListener(this);
    }

    public void paint(Graphics g) {
        // if there is no input, display text telling the player what to do
        if(builder.isEmpty()) {
            g.setColor(Color.gray);
            Text.drawText(10, 10, .7, g, "enter your word or phrase to start the game...", getWidth() - 20);
        }

        Text.drawText(10, 10, .7, g, builder.toString(), getWidth() - 20);

        // show limits on characters
        if(builder.length() > 40 || builder.isEmpty()) {
            g.setColor(Color.red);
        }
        Text.drawText(10, getHeight() - 90, .7, g, builder.length() + "/40", getWidth() - 20);
        g.setColor(Color.black);
        Text.drawText(10, getHeight() - 50, .5, g, "Press [Enter] to continue", getWidth() - 20);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // remove a character if the delete key is typed
        if(e.getExtendedKeyCode() == KeyEvent.VK_DELETE
        || e.getExtendedKeyCode() == 8) {
            if(builder.isEmpty()) {
                return;
            }

            builder.delete(builder.length() - 1, builder.length());
        } else if(e.getExtendedKeyCode() == KeyEvent.VK_ENTER) {
            // if enter is typed and the string is the appropriate length, run the `onSubmit`
            // callback
            if(builder.length() <= 40 && !builder.isEmpty()) {
                onSubmit.accept(builder.toString());
            }
            return;
        } else {
            builder.append(e.getKeyChar());
        }

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
