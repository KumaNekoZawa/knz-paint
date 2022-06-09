package knz.paint.model.tools.specific;

import java.awt.Graphics2D;

import knz.paint.model.tools.MouseInfo;

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
    public boolean doesChangeCanvas() {
        return mousePressed;
    }

    @Override
    public void mouseReleased(Graphics2D graphics2d, MouseInfo e) {
        super.mouseReleased(graphics2d, e);
        drawLine(graphics2d);
    }

    @Override
    public void paint(Graphics2D graphics2d) {
        super.paint(graphics2d);
        drawLine(graphics2d);
    }

    private void drawLine(Graphics2D graphics2d) {
        graphics2d.setColor(toolState.getColorPrimary());
        graphics2d.setStroke(toolState.getStroke());
        graphics2d.drawLine(startX, startY, x, y);
    }

}
