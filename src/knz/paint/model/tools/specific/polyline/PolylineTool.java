package knz.paint.model.tools.specific.polyline;

import java.awt.Graphics2D;

import knz.paint.model.tools.MouseInfo;

public class PolylineTool extends AbstractPolylineTool {

    @Override
    public String getName() {
        return "Polyline";
    }

    @Override
    public String getIcon() {
        return "tool_13b.png";
    }

    @Override
    public boolean doesChangeCanvas() {
        return polygon.npoints > 0;
    }

    @Override
    public void mousePressed(Graphics2D graphics2d, MouseInfo e) {
        super.mousePressed(graphics2d, e);
        if (e.isLeftButton()) {
            if (polygon.npoints == 0) {
                polygon.addPoint(x, y);
            }
        } else if (e.isRightButton()) {
            drawPolyline(graphics2d, false);
            polygon.reset();
        }
    }

    @Override
    public void mouseReleased(Graphics2D graphics2d, MouseInfo e) {
        super.mouseReleased(graphics2d, e);
        if (e.isLeftButton()) {
            polygon.addPoint(x, y);
        }
    }

    @Override
    public void paint(Graphics2D graphics2d) {
        super.paint(graphics2d);
        drawPolyline(graphics2d, true);
    }

}
