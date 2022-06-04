package knz.paint.model.tools.specific;

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
    public boolean doesChangeCanvas() {
        return mousePressed;
    }

    @Override
    public void mouseReleased(Graphics2D graphics2d, MouseEvent e) {
        super.mouseReleased(graphics2d, e);
        drawRectangle(graphics2d);
    }

    @Override
    public void paint(Graphics2D graphics2d) {
        super.paint(graphics2d);
        drawRectangle(graphics2d);
    }

    private void drawRectangle(Graphics2D graphics2d) {
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
            graphics2d.drawRect(fromX, fromY, width, height);
            break;
        case PRIMARY:
            graphics2d.setColor(colorPrimary);
            graphics2d.fillRect(fromX, fromY, width, height);
            break;
        case SECONDARY:
            graphics2d.setColor(colorSecondary);
            graphics2d.fillRect(fromX, fromY, width, height);
            graphics2d.setColor(colorPrimary);
            graphics2d.drawRect(fromX, fromY, width, height);
            break;
        default:
            throw new AssertionError();
        }
    }

}
