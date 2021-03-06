package knz.paint.model.tools.specific.polyline;

import java.awt.Graphics2D;

import knz.paint.model.tools.MouseInfo;

public class PolylineFreeFormTool extends AbstractPolylineTool {

    @Override
    public String getName() {
        return "Polyline free-form (Brush)";
    }

    @Override
    public String getIcon() {
        return "tool_7.png";
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
            /* twice, so that the first pixel gets drawn no matter what */
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
        drawPolyline(graphics2d, false);
        polygon.reset();
    }

    @Override
    public void paint(Graphics2D graphics2d) {
        super.paint(graphics2d);
        drawPolyline(graphics2d, false);
    }

}
