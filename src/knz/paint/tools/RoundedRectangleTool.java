package knz.paint.tools;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public class RoundedRectangleTool extends AbstractTool {

    private int arcWidth = 20;
    private int arcHeight = 20;

    @Override
    public String getName() {
        return "Rounded rectangle";
    }

    @Override
    public String getIcon() {
        return "tool_15.png";
    }

    @Override
    public void mousePressed(Graphics2D g2d, MouseEvent e) {
        super.mousePressed(g2d, e);
        arcWidth = mainPanel.getRoundedRectangleArcWidth();
        arcHeight = mainPanel.getRoundedRectangleArcHeight();
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
        g2d.setStroke(mainPanel.getStroke());
        switch (mainPanel.getFillStyle()) {
        case NONE:
            g2d.setColor(colorPrimary);
            g2d.drawRoundRect(fromX, fromY, width, height, arcWidth, arcHeight);
            break;
        case PRIMARY:
            g2d.setColor(colorPrimary);
            g2d.fillRoundRect(fromX, fromY, width, height, arcWidth, arcHeight);
            break;
        case SECONDARY:
            g2d.setColor(colorSecondary);
            g2d.fillRoundRect(fromX, fromY, width, height, arcWidth, arcHeight);
            g2d.setColor(colorPrimary);
            g2d.drawRoundRect(fromX, fromY, width, height, arcWidth, arcHeight);
            break;
        default:
            throw new AssertionError();
        }
    }

}
