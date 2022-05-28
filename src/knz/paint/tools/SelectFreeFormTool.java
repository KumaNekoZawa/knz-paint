package knz.paint.tools;

import java.awt.event.MouseEvent;
import java.awt.Graphics2D;
import java.awt.Polygon;

import knz.paint.view.MainPanel;

public class SelectFreeFormTool extends AbstractSelectionTool {

    public SelectFreeFormTool(MainPanel mainPanel) {
        super(mainPanel);
    }

    @Override
    public void mousePressed(Graphics2D g2d, MouseEvent e) {
        super.mousePressed(g2d, e);
        polygon.reset();
        polygon.addPoint(x, y);
    }

    @Override
    public void mouseDragged(Graphics2D g2d, MouseEvent e) {
        super.mouseDragged(g2d, e);
        polygon.addPoint(x, y);
    }

    @Override
    public void paint(Graphics2D g2d) {
        super.paint(g2d);
        drawSelection(g2d, false);
    }

}
