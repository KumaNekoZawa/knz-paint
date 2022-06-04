package knz.paint.model.tools.specific;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public class RoundedRectangleTool extends AbstractTool {

    @Override
    public String getName() {
        return "Rounded rectangle";
    }

    @Override
    public String getIcon() {
        return "tool_15.png";
    }

    @Override
    public boolean doesChangeCanvas() {
        return mousePressed;
    }

    @Override
    public void mouseReleased(Graphics2D graphics2d, MouseEvent e) {
        super.mouseReleased(graphics2d, e);
        drawRoundedRectangle(graphics2d);
    }

    @Override
    public void paint(Graphics2D graphics2d) {
        super.paint(graphics2d);
        drawRoundedRectangle(graphics2d);
    }

    private void drawRoundedRectangle(Graphics2D graphics2d) {
        final int fromX = Math.min(startX, x);
        final int fromY = Math.min(startY, y);
        final int toX = Math.max(startX, x);
        final int toY = Math.max(startY, y);
        final int width  = toX - fromX;
        final int height = toY - fromY;
        final Color colorPrimary   = toolState.getColorPrimary();
        final Color colorSecondary = toolState.getColorSecondary();
        final int arcWidth  = toolState.getRoundedRectangleArcWidth();
        final int arcHeight = toolState.getRoundedRectangleArcHeight();
        graphics2d.setStroke(toolState.getStroke());
        switch (toolState.getFillStyle()) {
        case NONE:
            graphics2d.setColor(colorPrimary);
            graphics2d.drawRoundRect(fromX, fromY, width, height, arcWidth, arcHeight);
            break;
        case PRIMARY:
            graphics2d.setColor(colorPrimary);
            graphics2d.fillRoundRect(fromX, fromY, width, height, arcWidth, arcHeight);
            break;
        case SECONDARY:
            graphics2d.setColor(colorSecondary);
            graphics2d.fillRoundRect(fromX, fromY, width, height, arcWidth, arcHeight);
            graphics2d.setColor(colorPrimary);
            graphics2d.drawRoundRect(fromX, fromY, width, height, arcWidth, arcHeight);
            break;
        default:
            throw new AssertionError();
        }
    }

}
