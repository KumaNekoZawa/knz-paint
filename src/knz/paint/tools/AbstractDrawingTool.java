package knz.paint.tools;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.Graphics2D;
import java.awt.Polygon;

import knz.paint.view.MainPanel;

// not for all Draw*Tool classes! only for DrawFreeHand and DrawPolygon!
public abstract class AbstractDrawingTool extends AbstractTool {

    protected Polygon polygon = new Polygon();

    public AbstractDrawingTool(MainPanel mainPanel) {
        super(mainPanel);
    }

    protected void drawPolygon(Graphics2D g2d, Polygon polygon, boolean includeCurrentPixel) {
        if ((includeCurrentPixel && (x < 0 || y < 0)) || polygon.npoints < 1) {
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
        }
    }

}
