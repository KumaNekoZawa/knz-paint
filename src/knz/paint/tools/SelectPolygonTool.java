package knz.paint.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public class SelectPolygonTool extends AbstractSelectionTool {

    @Override
    public String getName() {
        return "Select polygon";
    }

    @Override
    public String getIcon() {
        return "tool_13c.png";
    }

    @Override
    public void mousePressed(Graphics2D graphics2d, MouseEvent e) {
        super.mousePressed(graphics2d, e);
        if (polygon.npoints == 0) {
            polygon.addPoint(x, y);
        }
    }

    @Override
    public void mouseReleased(Graphics2D graphics2d, MouseEvent e) {
        super.mouseReleased(graphics2d, e);
        polygon.addPoint(x, y);
    }

    @Override
    public void paint(Graphics2D graphics2d) {
        super.paint(graphics2d);
        drawSelection(graphics2d, true);
    }

}
