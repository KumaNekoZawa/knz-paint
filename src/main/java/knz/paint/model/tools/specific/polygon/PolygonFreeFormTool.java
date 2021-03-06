package knz.paint.model.tools.specific.polygon;

import java.awt.Graphics2D;

import knz.paint.model.tools.MouseInfo;

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
    public boolean doesChangeCanvas() {
        return mousePressed;
    }

    @Override
    public void mousePressed(Graphics2D graphics2d, MouseInfo e) {
        super.mousePressed(graphics2d, e);
        polygon.reset();
        if (e.isLeftButton()) {
            polygon.addPoint(x, y);
        }
    }

    @Override
    public void mouseDragged(Graphics2D graphics2d, MouseInfo e) {
        super.mouseDragged(graphics2d, e);
        polygon.addPoint(x, y);
    }

    @Override
    public void mouseReleased(Graphics2D graphics2d, MouseInfo e) {
        super.mouseReleased(graphics2d, e);
        drawPolygon(graphics2d, false);
        polygon.reset();
    }

    @Override
    public void paint(Graphics2D graphics2d) {
        super.paint(graphics2d);
        drawPolygon(graphics2d, false);
    }

}
