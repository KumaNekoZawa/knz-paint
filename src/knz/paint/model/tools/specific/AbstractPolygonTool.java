package knz.paint.model.tools.specific;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

public abstract class AbstractPolygonTool extends AbstractTool {

    protected Polygon polygon = new Polygon();

    protected void drawPolygon(Graphics2D graphics2d, boolean includeCurrentPixel) {
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
        final Color colorPrimary   = toolState.getColorPrimary();
        final Color colorSecondary = toolState.getColorSecondary();
        graphics2d.setStroke(toolState.getStroke());
        switch (toolState.getFillStyle()) {
        case NONE:
            graphics2d.setColor(colorPrimary);
            graphics2d.drawPolygon(polygon2);
            break;
        case PRIMARY:
            graphics2d.setColor(colorPrimary);
            graphics2d.fillPolygon(polygon2);
            /* workaround: otherwise there would be nothing drawn for npoints <= 3 */
            graphics2d.drawPolygon(polygon2);
            break;
        case SECONDARY:
            graphics2d.setColor(colorSecondary);
            graphics2d.fillPolygon(polygon2);
            graphics2d.setColor(colorPrimary);
            graphics2d.drawPolygon(polygon2);
            break;
        default:
            throw new AssertionError();
        }
    }

}
