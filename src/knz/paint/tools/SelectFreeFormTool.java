package knz.paint.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public class SelectFreeFormTool extends AbstractSelectionTool {

    @Override
    public String getName() {
        return "Select free-form";
    }

    @Override
    public String getIcon() {
        return "tool_0.png";
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
    public void paint(Graphics2D g2d) {
        super.paint(g2d);
        drawSelection(g2d, false);
    }

}