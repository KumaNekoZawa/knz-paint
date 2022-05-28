package knz.paint.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class AirbrushTool extends AbstractTool {

    private final int RADIUS = 10; // FIXME make variable

    @Override
    public String getName() {
        return "Airbrush";
    }

    @Override
    public String getIcon() {
        return "tool_8.png";
    }

    @Override
    public void mousePressed(Graphics2D g2d, MouseEvent e) {
        super.mousePressed(g2d, e);
        drawAirbrush();
    }

    @Override
    public void mouseDragged(Graphics2D g2d, MouseEvent e) {
        super.mouseDragged(g2d, e);
        drawAirbrush();
    }

    @Override
    public void mouseReleased(Graphics2D g2d, MouseEvent e) {
        super.mouseReleased(g2d, e);
        drawAirbrush();
    }

    private void drawAirbrush() {
        final double a = Math.random() * 2.0 * Math.PI;
        final double d = RADIUS * Math.random();
        final int lx = x + (int) (d * Math.sin(a));
        final int ly = y + (int) (d * Math.cos(a));
        BufferedImage image = mainPanel.getImage();
        if (0 <= lx && lx < image.getWidth()
         && 0 <= ly && ly < image.getHeight()) {
            // TODO also support these random color modes:
            //Color.HSBtoRGB((float) Math.random(), 1f, 1f)
            //new Color((int) (255 * Math.random()), (int) (255 * Math.random()), (int) (255 * Math.random())).getRGB()
            image.setRGB(lx, ly, mainPanel.getColorPrimary().getRGB());
        }
    }

}
