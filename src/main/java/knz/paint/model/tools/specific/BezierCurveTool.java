package knz.paint.model.tools.specific;

import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.Arrays;

import knz.paint.model.tools.MouseInfo;

public class BezierCurveTool extends AbstractTool {

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
    public boolean doesChangeCanvas() {
        return polygon.npoints > 0;
    }

    @Override
    public void mousePressed(Graphics2D graphics2d, MouseInfo e) {
        super.mousePressed(graphics2d, e);
        if (e.isLeftButton()) {
            if (polygon.npoints == 0) {
                polygon.addPoint(x, y);
            }
        } else if (e.isRightButton()) {
            drawBezierCurve(graphics2d, false);
            polygon.reset();
        }
    }

    @Override
    public void mouseReleased(Graphics2D graphics2d, MouseInfo e) {
        super.mouseReleased(graphics2d, e);
        if (e.isLeftButton()) {
            polygon.addPoint(x, y);
        }
    }

    @Override
    public void paint(Graphics2D graphics2d) {
        super.paint(graphics2d);
        drawBezierCurve(graphics2d, true);
    }

    private void drawBezierCurve(Graphics2D graphics2d, boolean includeCurrentPixel) {
        if (polygon.npoints < 1) {
            return;
        }
        final Polygon polygon2;
        if (includeCurrentPixel) {
            polygon2 = new Polygon(polygon.xpoints, polygon.ypoints, polygon.npoints);
            polygon2.addPoint(x, y);
        } else {
            polygon2 = polygon;
        }
        graphics2d.setColor(toolState.getColorPrimary());
        graphics2d.setStroke(toolState.getStroke());

        final int minX = Arrays.stream(polygon2.xpoints).min().orElse(0);
        final int minY = Arrays.stream(polygon2.ypoints).min().orElse(0);
        final int maxX = Arrays.stream(polygon2.xpoints).max().orElse(0);
        final int maxY = Arrays.stream(polygon2.ypoints).max().orElse(0);
        final int dx = maxX - minX;
        final int dy = maxY - minY;
        final double d = Math.sqrt(dx * dx + dy * dy);
        final int quality = d >= 1 ? Math.max((int) d / 3, 1) : 1;

        final Polygon polygonFinal = new Polygon();
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

            polygonFinal.addPoint((int) xcoords[0], (int) ycoords[0]);
        }
        graphics2d.drawPolyline(polygonFinal.xpoints, polygonFinal.ypoints, polygonFinal.npoints);
    }

    private double[] calcBezier(double[] coords, double ratio) {
        final double[] result = new double[coords.length - 1];
        for (int i = 0; i < result.length; i++) {
            result[i] = inBetween(coords[i], coords[i + 1], ratio);
        }
        return result;
    }

    private static double inBetween(double a, double b, double ratio) {
        if (!(0 <= ratio && ratio <= 1)) {
            throw new IllegalArgumentException();
        }
        final double negRatio = 1 - ratio;
        return negRatio * a + ratio * b;
    }

}
