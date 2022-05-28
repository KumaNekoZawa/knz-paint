package knz.paint.tools;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public class RectangleTool extends AbstractTool {

    @Override
    public String getName() {
        return "Rectangle";
    }

    @Override
    public String getIcon() {
        return "tool_12.png";
    }

    @Override
    public void mouseReleased(Graphics2D g2d, MouseEvent e) {
        super.mouseReleased(g2d, e);
        drawRectangle(g2d);
    }

    @Override
    public boolean needsRepaint() {
        return mousePressed;
    }

    @Override
    public void paint(Graphics2D g2d) {
        super.paint(g2d);
        drawRectangle(g2d);
    }

    private void drawRectangle(Graphics2D g2d) {
        final int fromX = Math.min(startX, x);
        final int fromY = Math.min(startY, y);
        final int toX = Math.max(startX, x);
        final int toY = Math.max(startY, y);
        final int width = toX - fromX;
        final int height = toY - fromY;
        Color colorPrimary = mainPanel.getColorPrimary();
        Color colorSecondary = mainPanel.getColorSecondary();
        switch (mainPanel.getFillStyle()) {
        case NONE:
            g2d.setColor(colorPrimary);
            g2d.drawRect(fromX, fromY, width, height);
            break;
        case PRIMARY:
            g2d.setColor(colorPrimary);
            g2d.fillRect(fromX, fromY, width, height);
            break;
        case SECONDARY:
            g2d.setColor(colorSecondary);
            g2d.fillRect(fromX, fromY, width, height);
            g2d.setColor(colorPrimary);
            g2d.drawRect(fromX, fromY, width, height);
            break;
        default:
            throw new AssertionError();
        }
    }

}
