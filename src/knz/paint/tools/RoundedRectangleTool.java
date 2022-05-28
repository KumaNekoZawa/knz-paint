package knz.paint.tools;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public class RoundedRectangleTool extends AbstractTool {

    private static final int ARC_WIDTH = 20; // FIXME make variable
    private static final int ARC_HEIGHT = 20; // FIXME make variable

    @Override
    public String getName() {
        return "Rounded rectangle";
    }

    @Override
    public String getIcon() {
        return "tool_15.png";
    }

    @Override
    public void mouseReleased(Graphics2D g2d, MouseEvent e) {
        super.mouseReleased(g2d, e);
        drawRoundedRectangle(g2d);
    }

    @Override
    public boolean needsRepaint() {
        return mousePressed;
    }

    @Override
    public void paint(Graphics2D g2d) {
        super.paint(g2d);
        drawRoundedRectangle(g2d);
    }

    private void drawRoundedRectangle(Graphics2D g2d) {
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
            g2d.drawRoundRect(fromX, fromY, width, height, ARC_WIDTH, ARC_HEIGHT);
            break;
        case PRIMARY:
            g2d.setColor(colorPrimary);
            g2d.fillRoundRect(fromX, fromY, width, height, ARC_WIDTH, ARC_HEIGHT);
            break;
        case SECONDARY:
            g2d.setColor(colorSecondary);
            g2d.fillRoundRect(fromX, fromY, width, height, ARC_WIDTH, ARC_HEIGHT);
            g2d.setColor(colorPrimary);
            g2d.drawRoundRect(fromX, fromY, width, height, ARC_WIDTH, ARC_HEIGHT);
            break;
        default:
            throw new AssertionError();
        }
    }

}
