package com.hangman.assets;

import java.awt.*;

public class Text {

    // draws a line of text
    // precondition: line is all lowercase
    public static void drawLine(int x, int y, double size, Graphics g, String line, int width) {
        // loop through each character and draw then one after the other
        for(int i = 0; i < line.length(); i++) {
            if(line.charAt(i) == ' ')
                continue;

            // find the special character or a-z character
            int[][][] character = switch(line.charAt(i)) {
                case '[' -> CharactersSpecial.lBracket;
                case ']' -> CharactersSpecial.rBracket;
                case '/' -> CharactersSpecial.slash;
                case '.' -> CharactersSpecial.period;
                case '_' -> Blanks.blanks[(int) (Math.random() * Blanks.blanks.length)];
                default -> Characters2.characters[6];
            };
            // Characters1 holds a-t
            if(line.charAt(i) >= 'a' && line.charAt(i) <= 't') {
                character = Characters1.characters[line.charAt(i) - 'a'];
            // Characters2 holds u-z and 0-9
            } else if(line.charAt(i) >= 'u' && line.charAt(i) <= 'z') {
                character = Characters2.characters[line.charAt(i) - 'u'];
            } else if(line.charAt(i) >= '0' && line.charAt(i) <= '9') {
                character = Characters2.characters[line.charAt(i) - '0' + 7];
            }

            // draw the actual character aligned by `i`
            Drawing.drawSaved(
                    (int) (x + (width - line.length()) * (20 * size) + i * (40 * size)),
                    y, size, character, g);
        }
    }

    public static void drawText(int x, int y, double size, Graphics g, String string, int width) {
        string = string.toLowerCase();

        // get each word and align the x to move each line to the center horizontally
        String[] words = string.split(" ");
        int line_width = (int) (width / (40 * size));
        x += (int) ((width - line_width * (40 * size)) / 2);

        // loop through each word and create a line that fits within the specified
        // `width`
        for(int i = 0; i < words.length; i++) {
            StringBuilder line = new StringBuilder(words[i]);

            // split the word if it is too large to fit in one line
            while(line.length() > line_width) {
                drawLine(x, y, size, g, line.substring(0, line_width), line_width);
                line = new StringBuilder(line.substring(line_width));
                y += (int) (50 * size);
            }

            while(i + 1 < words.length && line.length() + words[i + 1].length() < line_width) {
                line.append(" ").append(words[++i]);
            }

            // draw the line and go to the next line
            drawLine(x, y, size, g, line.toString(), line_width);
            y += (int) (50 * size);
        }
    }

}
