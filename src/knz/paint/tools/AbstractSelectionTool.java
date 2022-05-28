package knz.paint.tools;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Stroke;

public abstract class AbstractSelectionTool extends AbstractTool {

    private static final Stroke STROKE = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{ 4.0f }, 0.0f);
    private static final Color COLOR = new Color(0x00, 0x00, 0x00, 0x80);

    protected Polygon polygon = new Polygon();

    @Override
    public Cursor getCursor() {
        return new Cursor(Cursor.CROSSHAIR_CURSOR);
    }

    @Override
    public boolean doesChangeImage() {
        return false;
    }

    @Override
    public boolean needsRepaint() {
        return true;
    }

    public void selectNone() {
        polygon.reset();
    }

    public void clearSelection() {
        if (polygon.npoints > 2) {
            Graphics2D g2d = mainPanel.getG2D();
            g2d.setColor(mainPanel.getColorSecondary());
            g2d.fillPolygon(polygon);
            polygon.reset();
        }
    }

    protected void drawSelection(Graphics2D g2d, boolean includeCurrentPixel) {
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
        Stroke stroke = g2d.getStroke();
        g2d.setColor(COLOR);
        g2d.setStroke(STROKE);
        g2d.drawPolygon(polygon2);
        g2d.setStroke(stroke);
    }

}
