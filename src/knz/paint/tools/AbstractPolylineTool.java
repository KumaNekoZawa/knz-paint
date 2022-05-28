package knz.paint.tools;

import java.awt.Graphics2D;
import java.awt.Polygon;

public abstract class AbstractPolylineTool extends AbstractTool {

    protected Polygon polygon = new Polygon();

    protected void drawPolyline(Graphics2D g2d, boolean includeCurrentPixel) {
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
        g2d.drawPolyline(polygon2.xpoints, polygon2.ypoints, polygon2.npoints);
    }

}
