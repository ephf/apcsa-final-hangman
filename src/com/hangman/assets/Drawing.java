package com.hangman.assets;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class Drawing extends Canvas implements MouseMotionListener, MouseListener {

    public record Point(int x, int y) {}

    private final ArrayList<ArrayList<Point>> points = new ArrayList<>();

    // The Drawing object is mostly used to create drawings that I can copy and past
    // into the source code like the hangman hanger and all of the characters
    public Drawing(int width, int height) {
        addMouseMotionListener(this);
        addMouseListener(this);
        setSize(width, height);

        // set the background so I can see the bounds for a character or drawing
        setBackground(new Color(255, 200, 200));
    }

    // helper overload that defaults the size to `1`
    public static void drawSaved(int x, int y, int[][][] image, Graphics g) {
        drawSaved(x, y, 1, image, g);
    }

    public static void drawSaved(int x, int y, double size, int[][][] image, Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke((float) (5 * size)));

        // draw a line between each point in the image, creating the actual image
        for(int[][] polyLine : image) {
            for(int i = 0; i < polyLine.length - 1; i++) {
                g.drawLine(
                        x + (int) (polyLine[i][0] * size), y + (int) (polyLine[i][1] * size),
                        x + (int) (polyLine[i + 1][0] * size), y + (int) (polyLine[i + 1][1] * size));
            }
        }
    }

    // again this function is mostly for internal use
    public void paint(Graphics g) {
        g.setColor(Color.black);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(5));

        // draw out everything that has been drawn up until this point
        for(ArrayList<Point> vertices : points) {
            for(int i = 0; i < vertices.size() - 1; i++) {
                g.drawLine(
                        vertices.get(i).x(), vertices.get(i).y(),
                        vertices.get(i + 1).x(), vertices.get(i + 1).y());
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // add a new point to the drawing and repaint
        points.getLast().add(new Point(e.getX(), e.getY()));
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        // create a new list of points (for each stroke) when the mouse is pressed
        // (when the drawer starts to draw something)
        if(points.isEmpty() || !points.getLast().isEmpty()) {
            points.add(new ArrayList<>());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {
        // turn all of the points into code that can be copy and pasted into
        // the source code
        // (an initial `,` has to be removed manually)
        StringBuilder output = new StringBuilder("{");
        for(ArrayList<Point> v : points) {
            output.append(",{");
            for(int i = 0; i < v.size(); i++) {
                if(i != 0) {
                    output.append(",");
                }
                output.append("{").append(v.get(i).x()).append(",")
                                  .append(v.get(i).y()).append("}");
            }
            output.append("}");
        }
        System.out.println(output.append("}"));
    }
}
