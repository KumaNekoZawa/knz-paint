package knz.paint.tools;

import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import javax.swing.SwingUtilities;

public class BezierCurveTool extends AbstractTool {

    // FIXME does not work well with dashes!

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
        g2d.setStroke(mainPanel.getStroke());

        /* calc quality */
        final int minX = Arrays.stream(polygon2.xpoints).min().orElse(0);
        final int minY = Arrays.stream(polygon2.ypoints).min().orElse(0);
        final int maxX = Arrays.stream(polygon2.xpoints).max().orElse(0);
        final int maxY = Arrays.stream(polygon2.ypoints).max().orElse(0);
        final int dx = maxX - minX;
        final int dy = maxY - minY;
        final double d = Math.sqrt(dx * dx + dy * dy);
        final int quality = d >= 1. ? Math.max((int) (d / 3.), 1) : 1;

        int lastX = -1, lastY = -1;
        for (int i = 0; i < quality; i++) {
            final double ratio = i / (double) quality;

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
