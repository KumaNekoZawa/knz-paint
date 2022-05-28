package knz.paint.tools;

import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;

public class BezierCurveTool extends AbstractTool {

    private final int QUALITY = 100;

    private Polygon polygon = new Polygon();

    @Override
    public String getName() {
        return "Bézier curve";
    }

    @Override
    public String getIcon() {
        return "tool_11.png";
    }

    @Override
    public void mousePressed(Graphics2D g2d, MouseEvent e) {
        super.mousePressed(g2d, e);
        if (SwingUtilities.isLeftMouseButton(e)) {
            if (polygon.npoints == 0) {
                polygon.addPoint(x, y);
            }
        } else if (SwingUtilities.isRightMouseButton(e)) {
            drawBezierCurve(g2d, false);
            polygon.reset();
        }
    }

    @Override
    public void mouseReleased(Graphics2D g2d, MouseEvent e) {
        super.mouseReleased(g2d, e);
        if (SwingUtilities.isLeftMouseButton(e)) {
            polygon.addPoint(x, y);
        }
    }

    @Override
    public boolean needsRepaint() {
        return polygon.npoints > 0;
    }

    @Override
    public void paint(Graphics2D g2d) {
        super.paint(g2d);
        drawBezierCurve(g2d, true);
    }

    private void drawBezierCurve(Graphics2D g2d, boolean includeCurrentPixel) {
        if (polygon.npoints < 1) {
            return;
        }
        Polygon polygon2;
        if (includeCurrentPixel) {
            polygon2 = new Polygon(polygon.xpoints, polygon.ypoints, polygon.npoints);
            polygon2.addPoint(x, y);
        } else {
            polygon2 = polygon;
        }
        g2d.setColor(mainPanel.getColorPrimary());
        int lastX = -1, lastY = -1;
        for (int i = 0; i < QUALITY; i++) {
            final double ratio = i / (double) QUALITY;

            double[] xcoords = new double[polygon2.npoints];
            for (int j = 0; j < xcoords.length; j++) {
                xcoords[j] = (double) polygon2.xpoints[j];
            }
            while (xcoords.length > 1) {
                xcoords = calcBezier(xcoords, ratio);
            }

            double[] ycoords = new double[polygon2.npoints];
            for (int j = 0; j < ycoords.length; j++) {
                ycoords[j] = (double) polygon2.ypoints[j];
            }
            while (ycoords.length > 1) {
                ycoords = calcBezier(ycoords, ratio);
            }

            final int x = (int) xcoords[0];
            final int y = (int) ycoords[0];
            if (i > 0) {
                g2d.drawLine(lastX, lastY, x, y);
            }
            lastX = x;
            lastY = y;
        }
    }

    private double[] calcBezier(double[] coords, double ratio) {
        double[] result = new double[coords.length - 1];
        for (int i = 0; i < result.length; i++) {
            result[i] = inBetween(coords[i], coords[i + 1], ratio);
        }
        return result;
    }

    private static double inBetween(double a, double b, double ratio) {
        if (!(0. <= ratio && ratio <= 1.)) {
            throw new IllegalArgumentException();
        }
        final double invRatio = 1. - ratio;
        return invRatio * a + ratio * b;
    }

}