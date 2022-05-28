package knz.paint.tools;

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
    public void mousePressed(Graphics2D g2d, MouseEvent e) {
        super.mousePressed(g2d, e);
        if (SwingUtilities.isLeftMouseButton(e)) {
            if (polygon.npoints == 0) {
                polygon.addPoint(x, y);
            }
        } else if (SwingUtilities.isRightMouseButton(e)) {
            drawPolygon(g2d, false);
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
        drawPolygon(g2d, true);
    }

}
