package knz.paint.tools;

import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import knz.paint.view.MainPanel;

public abstract class AbstractTool {

    public static final Cursor CURSOR_DEFAULT = new Cursor(Cursor.DEFAULT_CURSOR);

    protected MainPanel mainPanel;
    protected int startX = 0, startY = 0;
    protected int x = 0, y = 0;
    protected boolean mousePressed = false;

    public final void setMainPanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public abstract String getName();

    public abstract String getIcon();

    public Cursor getCursor() {
        return CURSOR_DEFAULT;
    }

    public boolean doesChangeImage() {
        return true;
    }

    public void mousePressed(Graphics2D g2d, MouseEvent e) {
        x = e.getX() / mainPanel.getZoomFactor();
        y = e.getY() / mainPanel.getZoomFactor();
        startX = x;
        startY = y;
        mousePressed = true;
    }

    public void mouseDragged(Graphics2D g2d, MouseEvent e) {
        x = e.getX() / mainPanel.getZoomFactor();
        y = e.getY() / mainPanel.getZoomFactor();
    }

    public void mouseReleased(Graphics2D g2d, MouseEvent e) {
        mousePressed = false;
        x = e.getX() / mainPanel.getZoomFactor();
        y = e.getY() / mainPanel.getZoomFactor();
    }

    /** whether the paint method should be called to draw something on the canvas layer of the image */
    public boolean needsRepaint() {
        return false;
    }

    public void paint(Graphics2D g2d) {
    }

}
