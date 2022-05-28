package knz.paint.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public class SelectRectangleTool extends AbstractSelectionTool {

    @Override
    public String getName() {
        return "Select rectangle";
    }

    @Override
    public String getIcon() {
        return "tool_1.png";
    }

    @Override
    public void mousePressed(Graphics2D g2d, MouseEvent e) {
        super.mousePressed(g2d, e);
        polygon.reset();
    }

    @Override
    public void mouseDragged(Graphics2D g2d, MouseEvent e) {
        super.mouseDragged(g2d, e);
        final int minX = Math.min(startX, x);
        final int minY = Math.min(startY, y);
        final int maxX = Math.max(startX, x);
        final int maxY = Math.max(startY, y);
        polygon.reset();
        polygon.addPoint(minX, minY);
        polygon.addPoint(maxX, minY);
        polygon.addPoint(maxX, maxY);
        polygon.addPoint(minX, maxY);
    }

    @Override
    public void paint(Graphics2D g2d) {
        super.paint(g2d);
        drawSelection(g2d, false);
    }

}
