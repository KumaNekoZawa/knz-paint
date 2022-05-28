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
        // FIXME add icon
        return "";
    }

    @Override
    public void mousePressed(Graphics2D g2d, MouseEvent e) {
        super.mousePressed(g2d, e);
        if (polygon.npoints == 0) {
            polygon.addPoint(x, y);
        }
    }

    @Override
    public void mouseReleased(Graphics2D g2d, MouseEvent e) {
        super.mouseReleased(g2d, e);
        polygon.addPoint(x, y);
    }

    @Override
    public void paint(Graphics2D g2d) {
        super.paint(g2d);
        drawSelection(g2d, true);
    }

}
