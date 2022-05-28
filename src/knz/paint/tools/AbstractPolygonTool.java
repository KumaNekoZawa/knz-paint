package knz.paint.tools;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

public abstract class AbstractPolygonTool extends AbstractTool {

    protected Polygon polygon = new Polygon();

    protected void drawPolygon(Graphics2D g2d, boolean includeCurrentPixel) {
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
        Color colorPrimary = mainPanel.getColorPrimary();
        Color colorSecondary = mainPanel.getColorSecondary();
        switch (mainPanel.getFillStyle()) {
        case NONE:
            g2d.setColor(colorPrimary);
            g2d.drawPolygon(polygon2);
            break;
        case PRIMARY:
            g2d.setColor(colorPrimary);
            g2d.fillPolygon(polygon2);
            /* workaround: otherwise there would be nothing drawn for npoints <= 3 */
            g2d.drawPolygon(polygon2);
            break;
        case SECONDARY:
            g2d.setColor(colorSecondary);
            g2d.fillPolygon(polygon2);
            g2d.setColor(colorPrimary);
            g2d.drawPolygon(polygon2);
            break;
        default:
            throw new AssertionError();
        }
    }

}