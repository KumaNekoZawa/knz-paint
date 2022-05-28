package knz.paint.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public class PolygonFreeFormTool extends AbstractPolygonTool {

    @Override
    public String getName() {
        return "Polygon free-form";
    }

    @Override
    public String getIcon() {
        return "tool_0b.png";
    }

    @Override
    public void mousePressed(Graphics2D g2d, MouseEvent e) {
        super.mousePressed(g2d, e);
        polygon.reset();
        polygon.addPoint(x, y);
    }

    @Override
    public void mouseDragged(Graphics2D g2d, MouseEvent e) {
        super.mouseDragged(g2d, e);
        polygon.addPoint(x, y);
    }

    @Override
    public void mouseReleased(Graphics2D g2d, MouseEvent e) {
        super.mouseReleased(g2d, e);
        drawPolygon(g2d, false);
        polygon.reset();
    }

    @Override
    public void paint(Graphics2D g2d) {
        super.paint(g2d);
        drawPolygon(g2d, false);
    }

}
