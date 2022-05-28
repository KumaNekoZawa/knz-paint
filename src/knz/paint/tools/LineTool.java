package knz.paint.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public class LineTool extends AbstractTool {

    @Override
    public String getName() {
        return "Line";
    }

    @Override
    public String getIcon() {
        return "tool_10.png";
    }

    @Override
    public void mouseReleased(Graphics2D g2d, MouseEvent e) {
        super.mouseReleased(g2d, e);
        drawLine(g2d);
    }

    @Override
    public boolean needsRepaint() {
        return mousePressed;
    }

    @Override
    public void paint(Graphics2D g2d) {
        super.paint(g2d);
        drawLine(g2d);
    }

    private void drawLine(Graphics2D g2d) {
        g2d.setColor(mainPanel.getColorPrimary());
        g2d.setStroke(mainPanel.getStroke());
        g2d.drawLine(startX, startY, x, y);
    }

}
