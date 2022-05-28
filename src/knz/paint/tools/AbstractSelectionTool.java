package knz.paint.tools;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Stroke;

import knz.paint.view.MainPanel;

public abstract class AbstractSelectionTool extends AbstractTool {

    protected Polygon polygon = new Polygon();

    public AbstractSelectionTool(MainPanel mainPanel) {
        super(mainPanel);
    }

    public void clearSelection(Graphics2D g2d) {
        if (polygon.npoints > 2) {
            g2d.setColor(mainPanel.getColorSecondary());
            g2d.fillPolygon(polygon);
            polygon.reset();
        }
    }

    protected void drawSelection(Graphics2D g2d, boolean includeCurrentPixel) {
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
        Stroke stroke = g2d.getStroke();
        g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{ 4.0f }, 0.0f));
        g2d.setColor(Color.BLACK);
        g2d.drawPolygon(polygon2);
        g2d.setStroke(stroke);
    }

}
