package knz.paint.model.tools.specific.polyline;

import java.awt.Graphics2D;
import java.awt.Polygon;

import knz.paint.model.tools.specific.AbstractTool;

public abstract class AbstractPolylineTool extends AbstractTool {

    protected Polygon polygon = new Polygon();

    protected void drawPolyline(Graphics2D graphics2d, boolean includeCurrentPixel) {
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
        graphics2d.drawPolyline(polygon2.xpoints, polygon2.ypoints, polygon2.npoints);
    }

}
