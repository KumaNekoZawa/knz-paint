package knz.paint.tools;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.Timer;

import knz.paint.model.Config;

public class AirbrushTool extends AbstractTool {

    private static final boolean USE_TIMER = Config.getConfig().getToolsAirbrushUseTimer();
    private static final int TICK_DELAY = Config.getConfig().getToolsAirbrushTickDelay();
    private static final int PIXELS_PER_TICK = Config.getConfig().getToolsAirbrushPixelsPerTick();

    private Timer timer = new Timer(TICK_DELAY, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < PIXELS_PER_TICK; i++) {
                drawAirbrush();
            }
            mainPanel.repaint();
        }
    });

    private int radius = 15;

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
        radius = mainPanel.getAirbrushSize();
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
        final double r = radius * Math.random();
        final double a = 2 * Math.PI * Math.random();
        final int lx = x + (int) (r * Math.sin(a));
        final int ly = y + (int) (r * Math.cos(a));
        BufferedImage image = mainPanel.getImage();
        if (0 <= lx && lx < image.getWidth()
         && 0 <= ly && ly < image.getHeight()) {
            int rgb;
            switch (mainPanel.getAirbrushType()) {
            case NORMAL:
                rgb = mainPanel.getColorPrimary().getRGB();
                break;
            case RANDOM_COLOR:
                rgb = new Color((int) (0xFF * Math.random()),
                                (int) (0xFF * Math.random()),
                                (int) (0xFF * Math.random())).getRGB();
                break;
            case RANDOM_HUE:
                rgb = Color.HSBtoRGB((float) Math.random(), 1f, 1f);
                break;
            default:
                throw new AssertionError();
            }
            image.setRGB(lx, ly, rgb);
        }
    }

}
