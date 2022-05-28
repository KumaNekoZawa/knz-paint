package knz.paint.tools;

import java.awt.event.MouseEvent;
import java.awt.Graphics2D;
import java.awt.Polygon;

import javax.swing.SwingUtilities;

import knz.paint.view.MainPanel;

public class DrawPolygonTool extends AbstractDrawingTool {

    public DrawPolygonTool(MainPanel mainPanel) {
        super(mainPanel);
    }

    @Override
    public void mousePressed(Graphics2D g2d, MouseEvent e) {
        super.mousePressed(g2d, e);
        if (SwingUtilities.isLeftMouseButton(e)) {
            if (polygon.npoints == 0) {
                polygon.addPoint(x, y);
            }
        } else if (SwingUtilities.isRightMouseButton(e)) {
            drawPolygon(g2d, polygon, false);
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
    public void paint(Graphics2D g2d) {
        super.paint(g2d);
        drawPolygon(g2d, polygon, true);
    }

}
