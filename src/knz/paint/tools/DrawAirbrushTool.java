package knz.paint.tools;

import java.awt.event.MouseEvent;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import knz.paint.view.MainPanel;

public class DrawAirbrushTool extends AbstractTool {

    private final int AIRBRUSH_SIZE = 10; // FIXME make variable

    public DrawAirbrushTool(MainPanel mainPanel) {
        super(mainPanel);
    }

    @Override
    public void mousePressed(Graphics2D g2d, MouseEvent e) {
        super.mousePressed(g2d, e);
        drawAirBrush();
    }

    @Override
    public void mouseDragged(Graphics2D g2d, MouseEvent e) {
        super.mouseDragged(g2d, e);
        drawAirBrush();
    }

    @Override
    public void mouseReleased(Graphics2D g2d, MouseEvent e) {
        super.mouseReleased(g2d, e);
        drawAirBrush();
    }

    private void drawAirBrush() {
        if (x < 0 || y < 0) {
            return;
        }
        final double a = Math.random() * 2.0 * Math.PI;
        final double d = AIRBRUSH_SIZE * Math.random();
        final int lx = x + (int) (d * Math.sin(a));
        final int ly = y + (int) (d * Math.cos(a));
        BufferedImage image = mainPanel.getImage();
        if (0 <= lx && lx < image.getWidth()
         && 0 <= ly && ly < image.getHeight()) {
            image.setRGB(lx, ly, mainPanel.getColorPrimary().getRGB());
        }
    }

}
