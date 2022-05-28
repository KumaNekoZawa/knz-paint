package knz.paint.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import knz.paint.view.MainPanel;

public abstract class AbstractTool {

    protected MainPanel mainPanel;
    protected int startX = 0, startY = 0;
    protected int x = 0, y = 0;
    protected boolean mousePressed = false;

    public final void setMainPanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public abstract String getName();

    public abstract String getIcon();

    public void mousePressed(Graphics2D g2d, MouseEvent e) {
        x = e.getX() / mainPanel.getZoomLevel();
        y = e.getY() / mainPanel.getZoomLevel();
        startX = x;
        startY = y;
        mousePressed = true;
    }

    public void mouseDragged(Graphics2D g2d, MouseEvent e) {
        x = e.getX() / mainPanel.getZoomLevel();
        y = e.getY() / mainPanel.getZoomLevel();
    }

    public void mouseReleased(Graphics2D g2d, MouseEvent e) {
        mousePressed = false;
        x = e.getX() / mainPanel.getZoomLevel();
        y = e.getY() / mainPanel.getZoomLevel();
    }

    /** whether the paint method should be called to draw something on the canvas layer of the image */
    public boolean needsRepaint() {
        return false;
    }

    public void paint(Graphics2D g2d) {
    }

}
