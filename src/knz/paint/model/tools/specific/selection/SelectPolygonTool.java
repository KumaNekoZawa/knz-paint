package knz.paint.model.tools.specific.selection;

import java.awt.Graphics2D;

import knz.paint.model.tools.MouseInfo;

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
    public void mousePressed(Graphics2D graphics2d, MouseInfo e) {
        super.mousePressed(graphics2d, e);
        if (polygon.npoints == 0) {
            polygon.addPoint(x, y);
        }
    }

    @Override
    public void mouseReleased(Graphics2D graphics2d, MouseInfo e) {
        super.mouseReleased(graphics2d, e);
        polygon.addPoint(x, y);
    }

    @Override
    public void paint(Graphics2D graphics2d) {
        super.paint(graphics2d);
        drawSelection(graphics2d, true);
    }

}
