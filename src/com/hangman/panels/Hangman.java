package com.hangman.panels;

import com.hangman.assets.Drawing;
import com.hangman.assets.Text;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class Hangman extends Canvas implements MouseMotionListener, MouseListener {

    // outlines for each of the body parts so players know where to draw them
    private final int[][][] bodyPartBounds = new int[][][] {
            {
                    { 160, 210, 210, 160 },
                    { 70,  70,  120, 120 }
            },
            {
                    { 170, 200, 200, 170 },
                    { 100, 100, 250, 250 }
            },
            {
                    { 190, 170, 100, 120 },
                    { 140, 160, 110, 90  }
            },
            {
                    { 180, 200, 270, 250 },
                    { 140, 160, 110, 90  }
            },
            {
                    { 190, 170, 120, 140 },
                    { 260, 240, 290, 310 }
            },
            {
                    { 180, 200, 250, 230 },
                    { 260, 240, 290, 310 }
            }
    };
    // names for displaying what body part to draw
    private final String[] bodyPartNames = new String[] {
            "head", "body", "left arm", "right arm", "left leg", "right leg"
    };
    private int bodyPartIndex = 0;

    // actual body part drawings
    private ArrayList<ArrayList<int[]>> bodyParts = new ArrayList<>();
    private ArrayList<int[]> bodyPart;
    private boolean drawing = false;

    public Hangman(int width, int height) {
        setSize(width, height);
        setVisible(true);
    }

    private boolean gameOver = false;

    public boolean isGameOver() {
        return gameOver;
    }

    public void paint(Graphics g) {
        // if the game is over, everything is drawn as gray and a "Game Over" text
        // is drawn (it is at the end of the function so it is on top of everything else)
        if(gameOver) {
            g.setColor(Color.gray);
        }

        Drawing.drawSaved(0, -20, hanger, g);

        // if the player is currently drawing a body part, show the outline and text indicating
        // what they are drawing
        if(drawing) {
            g.setColor(new Color(255, 0, 0, 30));
            g.fillPolygon(
                    bodyPartBounds[bodyPartIndex][0],
                    bodyPartBounds[bodyPartIndex][1],
                    4);
            g.setColor(Color.red);
            Text.drawText(10, 10, .3, g, "Draw the " + bodyPartNames[bodyPartIndex], getWidth() - 20);
            g.setColor(Color.black);
        }

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(5));

        // draw each of the body parts, same way as `Drawing.drawSaved`
        for(ArrayList<int[]> bodyPart : bodyParts) {
            for(int i = 0; i < bodyPart.size() - 1; i++) {
                g.drawLine(
                        bodyPart.get(i)[0], bodyPart.get(i)[1],
                        bodyPart.get(i + 1)[0], bodyPart.get(i + 1)[1]);
            }
        }

        // draw "Game Over" text if the game is over
        if(gameOver) {
            g.setColor(Color.red);
            Text.drawText(10, (getHeight() - 40) / 2, .7, g, "Game Over", getWidth() - 20);
        }
    }

    private Runnable drawingFinishedCallback;

    public void nextPart(Runnable callback) {
        // add a new body part drawing and start listening for mouse events
        bodyPart = new ArrayList<>();
        bodyParts.add(bodyPart);
        drawingFinishedCallback = callback;
        drawing = true;

        addMouseListener(this);
        addMouseMotionListener(this);
        repaint();
    }

    private final int[][][] hanger = {{{39,345},{39,345},{40,345},{41,345},{43,345},{44,345},{45,346},{46,346},{47,346},{48,346},{49,346},{49,346},{50,346},{51,346},{52,346},{53,346},{54,346},{55,346},{56,347},{58,347},{59,347},{60,347},{61,348},{62,348},{63,348},{64,348},{66,348},{67,348},{68,348},{69,348},{70,348},{71,348},{71,348},{72,348},{74,348},{75,348},{76,348},{77,348},{78,348},{79,348},{81,348},{82,348},{83,348},{84,348},{86,347},{87,347},{89,347},{91,346},{92,346},{93,346},{94,346},{94,346},{95,346},{96,346},{97,346},{98,346},{100,346},{102,346},{103,346},{105,346},{107,346},{108,346},{110,346},{112,346},{115,346},{117,346},{120,346},{122,346},{124,347},{126,347},{128,348},{129,348},{131,348},{132,348},{132,348},{133,348},{134,348},{134,348},{135,348},{135,348},{136,348},{137,348},{137,348},{138,348},{139,348},{140,348},{141,348},{142,348},{143,348},{143,348},{144,348},{145,348},{146,348},{147,347},{148,347},{149,347},{150,346},{150,346},{151,346},{152,346},{153,346},{154,346},{154,346},{155,346},{156,346},{156,346},{157,346},{158,346},{159,346},{159,346},{160,346},{161,346},{162,346},{164,346},{166,346},{168,346},{170,346},{171,346},{173,346},{173,346},{174,346},{175,346},{175,346},{176,346},{176,346},{176,346},{177,346},{177,346},{178,346},{178,346},{179,346},{179,345},{180,345},{180,345},{181,345},{183,345},{184,345},{186,345},{189,345},{192,345},{195,345},{197,345},{200,345},{201,345},{203,345},{203,345},{204,345},{204,345},{205,345},{205,345},{205,345},{205,345},{206,345},{206,345},{207,345},{209,345},{210,345},{211,345},{213,345},{215,345},{217,345},{219,345},{220,345},{222,345},{223,345},{225,345},{226,345},{227,345},{228,345},{229,345},{230,345},{231,345},{232,345},{232,345},{233,345},{233,344},{234,344},{234,344},{235,344},{236,344},{238,344},{239,344},{241,344},{243,344},{245,344},{246,344},{247,344},{248,344},{249,344},{249,344},{249,343},{250,343},{249,343}},{{82,344},{82,343},{82,339},{82,335},{82,331},{82,327},{82,325},{82,322},{82,320},{82,317},{82,315},{82,313},{82,311},{82,309},{82,308},{82,306},{82,304},{82,303},{82,301},{82,300},{82,298},{83,296},{84,294},{84,292},{85,290},{85,288},{85,286},{85,285},{85,284},{85,283},{85,283},{85,282},{85,281},{85,280},{85,278},{85,277},{85,275},{85,273},{85,272},{85,270},{85,268},{85,266},{85,264},{85,261},{85,258},{85,254},{85,252},{85,250},{85,248},{85,245},{85,243},{85,241},{85,239},{85,236},{85,234},{85,231},{85,229},{85,227},{85,226},{85,224},{85,222},{85,219},{84,216},{84,213},{83,210},{83,208},{83,207},{83,206},{83,204},{83,202},{84,201},{84,200},{84,198},{84,197},{84,195},{84,194},{84,192},{84,190},{85,189},{85,187},{85,184},{85,182},{85,180},{85,179},{85,177},{85,176},{85,175},{85,175},{85,174},{85,173},{85,172},{84,170},{84,168},{84,166},{84,163},{84,161},{84,159},{84,157},{84,156},{84,154},{84,152},{84,151},{84,149},{84,147},{84,146},{84,144},{84,142},{84,139},{84,136},{84,133},{84,131},{84,129},{84,128},{84,127},{84,125},{84,123},{84,121},{84,119},{84,117},{83,116},{83,114},{83,113},{82,111},{82,110},{82,109},{81,107},{81,106},{81,105},{81,103},{81,102},{81,101},{81,100},{81,99},{81,98},{81,98},{81,97},{81,96},{81,95},{81,94},{81,92},{81,91},{81,90},{81,88},{81,88},{81,87},{81,85},{81,84},{81,82},{81,81},{81,81},{81,80},{81,80},{81,79},{81,79},{81,78},{81,78},{81,76},{81,75},{81,74},{81,73},{81,73},{81,73},{81,72},{81,72},{81,72},{81,71},{81,71},{81,70},{81,70},{81,69},{81,69},{81,69},{81,69},{83,69},{84,69},{86,68},{88,68},{90,68},{91,68},{93,68},{94,67},{95,67},{95,66},{96,66},{98,65},{99,65},{101,64},{102,63},{104,62},{105,62},{106,61},{108,61},{109,61},{109,61},{110,60},{111,60},{112,60},{113,60},{114,60},{115,60},{116,60},{116,60},{117,60},{117,60},{118,60},{119,60},{120,60},{122,60},{123,60},{125,60},{126,60},{127,60},{128,60},{129,60},{130,60},{131,60},{132,60},{133,60},{134,60},{134,60},{135,60},{136,60},{137,60},{138,60},{139,60},{140,60},{141,60},{142,60},{143,60},{144,60},{146,60},{147,61},{148,61},{149,61},{150,61},{151,61},{152,61},{153,61},{154,61},{155,61},{156,61},{158,62},{159,62},{161,62},{162,62},{163,62},{164,62},{165,62},{165,62},{166,62},{166,62},{166,62},{167,62},{167,62},{168,62},{168,62},{169,62},{170,62},{171,62},{171,62},{172,62},{173,62},{173,62},{174,62},{174,62},{175,62},{176,62},{176,62},{177,62},{178,62},{179,62},{179,62},{180,62},{180,62},{180,62},{180,62},{181,62},{181,62},{182,62},{182,62},{183,62},{183,62},{183,62},{183,62},{184,62},{184,62},{184,62},{184,63},{184,63},{184,63},{184,63},{184,64},{184,64},{184,64},{184,65},{184,65},{184,65},{184,66},{184,66},{184,67},{184,68},{184,69},{184,70},{184,71},{184,72},{184,73},{184,74},{184,75},{184,76},{184,76},{184,77},{184,78},{184,78},{184,79},{184,79},{184,80},{184,80},{184,81},{184,81},{184,82},{184,83},{184,84},{184,85},{184,85},{184,86},{184,86},{184,87},{184,87},{184,88},{184,88},{184,88},{184,89},{184,89},{184,90},{184,90},{184,90},{184,91},{184,91},{184,91},{184,91}},{{84,94},{84,93},{84,92},{85,91},{86,90},{87,89},{88,88},{89,87},{90,85},{91,84},{92,82},{93,81},{93,79},{94,78},{94,78},{95,77},{95,77},{95,76},{96,76},{96,75},{97,75},{97,74},{97,74},{98,73},{98,73},{99,72},{99,71},{100,69},{101,68},{102,67},{102,67},{103,66},{103,66},{104,66},{104,66},{104,66},{104,66},{105,65},{105,65},{105,65},{106,64},{106,63},{107,63},{107,62},{108,62},{108,61},{109,61},{109,61},{108,61},{108,61},{107,61},{106,61},{105,61},{104,61},{103,61},{102,61},{101,61},{100,61},{100,61},{99,61},{98,61},{97,61},{96,61},{96,61},{95,61},{94,61},{93,61},{92,61},{92,61},{91,61},{89,61},{88,61},{87,61},{86,61},{85,61},{84,61},{83,61},{83,61},{82,61},{82,61},{82,61},{81,61},{81,61},{81,61},{80,61},{80,61},{80,62},{80,62},{80,62},{80,62},{81,63},{81,63},{82,65},{82,66},{83,67},{83,68},{84,68}},{{84,319},{84,320},{85,323},{86,325},{87,326},{88,327},{88,328},{89,328},{89,329},{90,330},{91,331},{92,333},{93,334},{93,335},{94,336},{94,336},{94,336},{94,337},{95,337},{95,337},{95,338},{96,338},{96,339},{97,339},{97,340},{97,340}},{{85,304},{85,304},{85,304},{85,305},{86,307},{88,309},{90,312},{93,315},{95,318},{97,321},{98,322},{99,324},{100,324},{100,325},{101,325},{101,326},{101,327},{102,328},{102,329},{103,330},{104,331},{104,332},{105,332},{105,333},{106,334},{107,335},{107,337},{108,338},{109,338},{109,339},{110,339},{110,340},{111,341},{112,342},{112,343},{113,344},{113,344},{114,344}},{{80,316},{80,316},{80,317},{79,319},{79,320},{78,322},{77,323},{76,325},{75,327},{74,328},{73,329},{73,330},{72,331},{71,333},{71,335},{70,337},{69,339},{68,341},{68,342},{68,342},{67,342},{67,343},{67,343},{67,343},{67,343},{67,343},{66,343},{66,343},{66,343},{66,344}}};

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {
        // drawing is finished when mouse is released
        bodyPartIndex++;
        drawing = false;

        removeMouseListener(this);
        removeMouseMotionListener(this);

        // if this is the last body part, the game is over
        if(bodyPartIndex >= bodyPartBounds.length) {
            gameOver = true;
        }

        drawingFinishedCallback.run();
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {
        // add the next point of the drawing
        bodyPart.add(new int[] { e.getX(), e.getY() });
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {}
}
