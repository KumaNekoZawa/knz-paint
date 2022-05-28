package knz.paint.tools;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.Graphics2D;

import knz.paint.view.MainPanel;
import knz.paint.view.MainPanel.FillStyle;

public class DrawOvalTool extends AbstractTool {

    public DrawOvalTool(MainPanel mainPanel) {
        super(mainPanel);
    }

    @Override
    public void mouseReleased(Graphics2D g2d, MouseEvent e) {
        super.mouseReleased(g2d, e);
        drawOval(g2d);
    }

    @Override
    public void paint(Graphics2D g2d) {
        super.paint(g2d);
        drawOval(g2d);
    }

    private void drawOval(Graphics2D g2d) {
        if (x < 0 || y < 0 || startX < 0 || startY < 0) {
            return;
        }
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
            g2d.drawOval(fromX, fromY, width, height);
            break;
        case PRIMARY:
            g2d.setColor(colorPrimary);
            g2d.fillOval(fromX, fromY, width, height);
            break;
        case SECONDARY:
            g2d.setColor(colorSecondary);
            g2d.fillOval(fromX, fromY, width, height);
            g2d.setColor(colorPrimary);
            g2d.drawOval(fromX, fromY, width, height);
            break;
        }
    }

}
