package knz.paint.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;

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
    public void mousePressed(Graphics2D g2d, MouseEvent e) {
        super.mousePressed(g2d, e);
        polygon.reset();
        if (SwingUtilities.isLeftMouseButton(e)) {
            polygon.addPoint(x, y);
            /* twice, so that the first pixel gets drawn no matter what */
            polygon.addPoint(x, y);
        }
    }

    @Override
    public void mouseDragged(Graphics2D g2d, MouseEvent e) {
        super.mouseDragged(g2d, e);
        polygon.addPoint(x, y);
    }

    @Override
    public void mouseReleased(Graphics2D g2d, MouseEvent e) {
        super.mouseReleased(g2d, e);
        drawPolyline(g2d, false);
        polygon.reset();
    }

    @Override
    public boolean needsRepaint() {
        return mousePressed;
    }

    @Override
    public void paint(Graphics2D g2d) {
        super.paint(g2d);
        drawPolyline(g2d, false);
    }

}
