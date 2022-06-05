package knz.paint.model.tools.specific.polygon;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;

public class PolygonTool extends AbstractPolygonTool {

    @Override
    public String getName() {
        return "Polygon";
    }

    @Override
    public String getIcon() {
        return "tool_13.png";
    }

    @Override
    public boolean doesChangeCanvas() {
        return polygon.npoints > 0;
    }

    @Override
    public void mousePressed(Graphics2D graphics2d, MouseEvent e) {
        super.mousePressed(graphics2d, e);
        if (SwingUtilities.isLeftMouseButton(e)) {
            if (polygon.npoints == 0) {
                polygon.addPoint(x, y);
            }
        } else if (SwingUtilities.isRightMouseButton(e)) {
            drawPolygon(graphics2d, false);
            polygon.reset();
        }
    }

    @Override
    public void mouseReleased(Graphics2D graphics2d, MouseEvent e) {
        super.mouseReleased(graphics2d, e);
        if (SwingUtilities.isLeftMouseButton(e)) {
            polygon.addPoint(x, y);
        }
    }

    @Override
    public void paint(Graphics2D graphics2d) {
        super.paint(graphics2d);
        drawPolygon(graphics2d, true);
    }

}
