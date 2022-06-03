package knz.paint.tools;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Stroke;

public abstract class AbstractSelectionTool extends AbstractTool {

    private static final Color COLOR = new Color(0x00, 0x00, 0x00, 0x80);
    private static final Stroke STROKE = new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0f, new float[]{ 4f }, 0f);

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
    public boolean doesChangeCanvas() {
        return true;
    }

    public void selectNone() {
        polygon.reset();
    }

    public void clearSelection() {
        if (polygon.npoints > 2) {
            final Graphics2D graphics2d = imageState.getGraphics2D();
            graphics2d.setColor(toolState.getColorSecondary());
            graphics2d.fillPolygon(polygon);
            polygon.reset();
        }
    }

    protected void drawSelection(Graphics2D graphics2d, boolean includeCurrentPixel) {
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
        final Stroke stroke = graphics2d.getStroke();
        graphics2d.setColor(COLOR);
        graphics2d.setStroke(STROKE);
        graphics2d.drawPolygon(polygon2);
        graphics2d.setStroke(stroke);
    }

}
