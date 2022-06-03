package knz.paint.tools;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public class EllipseTool extends AbstractTool {

    @Override
    public String getName() {
        return "Ellipse";
    }

    @Override
    public String getIcon() {
        return "tool_14.png";
    }

    @Override
    public boolean doesChangeCanvas() {
        return mousePressed;
    }

    @Override
    public void mouseReleased(Graphics2D graphics2d, MouseEvent e) {
        super.mouseReleased(graphics2d, e);
        drawOval(graphics2d);
    }

    @Override
    public void paint(Graphics2D graphics2d) {
        super.paint(graphics2d);
        drawOval(graphics2d);
    }

    private void drawOval(Graphics2D graphics2d) {
        final int fromX = Math.min(startX, x);
        final int fromY = Math.min(startY, y);
        final int toX = Math.max(startX, x);
        final int toY = Math.max(startY, y);
        final int width  = toX - fromX;
        final int height = toY - fromY;
        final Color colorPrimary   = toolState.getColorPrimary();
        final Color colorSecondary = toolState.getColorSecondary();
        graphics2d.setStroke(toolState.getStroke());
        switch (toolState.getFillStyle()) {
        case NONE:
            graphics2d.setColor(colorPrimary);
            graphics2d.drawOval(fromX, fromY, width, height);
            break;
        case PRIMARY:
            graphics2d.setColor(colorPrimary);
            graphics2d.fillOval(fromX, fromY, width, height);
            break;
        case SECONDARY:
            graphics2d.setColor(colorSecondary);
            graphics2d.fillOval(fromX, fromY, width, height);
            graphics2d.setColor(colorPrimary);
            graphics2d.drawOval(fromX, fromY, width, height);
            break;
        default:
            throw new AssertionError();
        }
    }

}
