package com.AppletsPackage;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

class Segment {
    private static double x = 0;
    final double RAD = 10;
    private final double length;
    private final BufferedImage segment;
    private final Color segmentColor;
    private final Color pointColor;
    private final Color backGroundColor;

    private Rectangle2D.Double r;
    private Ellipse2D.Double p;
    private final double rotationAxis;

    private final Point2D.Double center;
    private double shift;
    private final int grad;

    Segment(final double length, final double posPointRotating, final int grad,
            final Color segmentColor, final Color pointColor, final Color backGroundColor)
            throws Exception {
        if (length <= 0 || posPointRotating < 0 || length < posPointRotating)
            throw new Exception("Error: wrong parameter in Segment class!");

        this.grad = grad;
        this.segmentColor = segmentColor;
        this.pointColor = pointColor;
        this.backGroundColor = backGroundColor;
        this.length = length;
        segment = new BufferedImage((int) length * 3, (int) length * 3,
                BufferedImage.TYPE_INT_ARGB);

        center = new Point2D.Double(length, 3 * length / 2);
        rotationAxis = center.x + posPointRotating - RAD / 2;
        r = new Rectangle2D.Double(center.x, center.y, length, RAD);
        p = new Ellipse2D.Double(rotationAxis, center.y, RAD, RAD);

        final Graphics2D g2 = segment.createGraphics();

        g2.setBackground(backGroundColor);
        g2.clearRect(0, 0, (int) (3 * length), (int) (3 * length));

        g2.setColor(segmentColor);
        g2.fill(r);
        g2.setColor(pointColor);
        g2.fill(p);
    }

    public void setPos(final double shiftX, final double shiftY) {
        this.shift = shiftX;
        center.y = center.y + shiftY * Math.sin(Math.toRadians(grad * x));
        r = new Rectangle2D.Double(center.x, center.y, length, RAD);
        p = new Ellipse2D.Double(rotationAxis + shift, center.y, RAD, RAD);
    }

    public void rotate() {
        final AffineTransform at = AffineTransform.getRotateInstance(
                Math.toRadians(grad * (++x)), rotationAxis + RAD / 2 + shift,
                center.y);

        final Graphics2D g2 = segment.createGraphics();

        g2.setBackground(backGroundColor);
        g2.clearRect(0, 0, (int) (3 * length), (int) (3 * length));

        g2.setTransform(at);
        g2.setColor(segmentColor);
        g2.fill(r);
        g2.setColor(pointColor);
        g2.fill(p);

    }

    public BufferedImage getSegment() {
        return segment;
    }
}
