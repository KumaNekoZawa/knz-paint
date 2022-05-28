package knz.paint.tools;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.Timer;

public class AirbrushTool extends AbstractTool {

    // FIXME make these variable
    private final int RADIUS = 15;
    private final boolean USE_RANDOM_COLORS = true;
    private final boolean USE_TIMER = true;
    private final int TICK_DELAY = 100;
    private final int PIXELS_PER_TICK = 10;

    private Timer timer = new Timer(TICK_DELAY, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < PIXELS_PER_TICK; i++) {
                drawAirbrush();
            }
            mainPanel.repaint();
        }
    });

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
        if (USE_TIMER) {
            timer.start();
        } else {
            drawAirbrush();
        }
    }

    @Override
    public void mouseDragged(Graphics2D g2d, MouseEvent e) {
        super.mouseDragged(g2d, e);
        if (!USE_TIMER) {
            drawAirbrush();
        }
    }

    @Override
    public void mouseReleased(Graphics2D g2d, MouseEvent e) {
        super.mouseReleased(g2d, e);
        if (USE_TIMER) {
            timer.stop();
        } else {
            drawAirbrush();
        }
    }

    private void drawAirbrush() {
        final double a = Math.random() * 2.0 * Math.PI;
        final double d = RADIUS * Math.random();
        final int lx = x + (int) (d * Math.sin(a));
        final int ly = y + (int) (d * Math.cos(a));
        BufferedImage image = mainPanel.getImage();
        if (0 <= lx && lx < image.getWidth()
         && 0 <= ly && ly < image.getHeight()) {
            int rgb;
            if (USE_RANDOM_COLORS) {
                rgb = Color.HSBtoRGB((float) Math.random(), 1f, 1f);
            } else {
                rgb = mainPanel.getColorPrimary().getRGB();
            }
            image.setRGB(lx, ly, rgb);
        }
    }

}
