package knz.paint.model.tools.specific.selection;

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
    public void mousePressed(Graphics2D graphics2d, MouseEvent e) {
        super.mousePressed(graphics2d, e);
        polygon.reset();
        polygon.addPoint(x, y);
    }

    @Override
    public void mouseDragged(Graphics2D graphics2d, MouseEvent e) {
        super.mouseDragged(graphics2d, e);
        polygon.addPoint(x, y);
    }

    @Override
    public void paint(Graphics2D graphics2d) {
        super.paint(graphics2d);
        drawSelection(graphics2d, false);
    }

}
