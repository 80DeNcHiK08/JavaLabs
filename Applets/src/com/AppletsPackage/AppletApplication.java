package com.AppletsPackage;

import java.applet.Applet;
import java.awt.*;
import java.awt.image.BufferedImage;

public class AppletApplication extends Applet implements Runnable {

    private static final long serialVersionUID = 1L;
    private int w, h;
    private BufferedImage bi;
    private Graphics2D big;
    private boolean stop = false;
    private Thread timer = null;

    private final Color fonColor = Color.lightGray;
    private final Color segmentColor = Color.BLACK;
    private final Color pointColor = Color.orange;
    private Segment segment;

    private double lengthSegment;

    private double movePoint = -1;
    private double shift = 0;
    private final double speedPoint = 1;

    private final int speedRepaint = 30;

    private final int grad = 15;

    public void init() {
        try {
            final Dimension dim = getSize();
            w = dim.width;
            h = dim.height;

            lengthSegment = (double) Math.min(w, h) / 3;
            segment = new Segment(lengthSegment, lengthSegment / 2, grad,
                    segmentColor, pointColor, fonColor);

            bi = (BufferedImage) createImage(w, h);
            big = bi.createGraphics();
            big.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            timer = new Thread(this);
            timer.start();
        } catch (final Exception e) {
            System.out.println(e);
        }
    }

    public void update(final Graphics g) {
        try {
            final Graphics2D g2 = (Graphics2D) g;
            drawSegment();

            g2.drawImage(bi, 0, 0, this);

        } catch (final Exception error) {
            System.out.println(error.getMessage());
        }
    }

    private void drawSegment() {
        shift += movePoint * speedPoint;
        if (shift < -lengthSegment / 2) {
            movePoint *= -1;
            shift = -lengthSegment / 2;
        } else if (shift > lengthSegment / 2) {
            movePoint *= -1;
            shift = lengthSegment / 2;
        }
        segment.setPos(shift, speedPoint);
        segment.rotate();
        big.drawImage(segment.getSegment(), null, 0, 0);
    }

    public void run() {
        while (!stop) {
            try {
                repaint();
                Thread.currentThread();
                Thread.sleep(speedRepaint);
            } catch (final Exception err) {
            }
        }
    }

    public void stop() {
        super.stop();
        stop = true;
    }

    public void start() {
        super.start();
        stop = false;
        if (timer == null) {
            timer = new Thread(this);
            timer.start();
        }
    }

    public void destroy() {
        super.destroy();
        stop = true;
        Thread.currentThread();
        Thread.yield();
    }

}
