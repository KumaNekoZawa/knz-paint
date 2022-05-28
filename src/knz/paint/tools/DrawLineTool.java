package knz.paint.tools;

import java.awt.event.MouseEvent;
import java.awt.Graphics2D;

import knz.paint.view.MainPanel;

public class DrawLineTool extends AbstractTool {

    public DrawLineTool(MainPanel mainPanel) {
        super(mainPanel);
    }

    @Override
    public void mouseReleased(Graphics2D g2d, MouseEvent e) {
        super.mouseReleased(g2d, e);
        drawLine(g2d);
    }

    @Override
    public void paint(Graphics2D g2d) {
        super.paint(g2d);
        drawLine(g2d);
    }

    private void drawLine(Graphics2D g2d) {
        if (x < 0 || y < 0 || startX < 0 || startY < 0) {
            return;
        }
        g2d.setColor(mainPanel.getColorPrimary());
        g2d.drawLine(startX, startY, x, y);
    }

}
