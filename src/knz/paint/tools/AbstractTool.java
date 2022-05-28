package knz.paint.tools;

import java.awt.event.MouseEvent;
import java.awt.Graphics2D;

import knz.paint.view.MainPanel;

public abstract class AbstractTool {

    protected MainPanel mainPanel;
    protected int startX, startY;
    protected int x, y;

    public AbstractTool(MainPanel mainPanel) {
        super();
        this.mainPanel = mainPanel;
    }

    public void mousePressed(Graphics2D g2d, MouseEvent e) {
        x = e.getX() / mainPanel.getZoomLevel();
        y = e.getY() / mainPanel.getZoomLevel();
        startX = x;
        startY = y;
    }

    public void mouseDragged(Graphics2D g2d, MouseEvent e) {
        x = e.getX() / mainPanel.getZoomLevel();
        y = e.getY() / mainPanel.getZoomLevel();
    }

    public void mouseReleased(Graphics2D g2d, MouseEvent e) {
        x = e.getX() / mainPanel.getZoomLevel();
        y = e.getY() / mainPanel.getZoomLevel();
    }

    public void paint(Graphics2D g2d) {
    }

}
