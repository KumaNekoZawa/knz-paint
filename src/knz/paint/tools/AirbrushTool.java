package knz.paint.tools;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import knz.paint.model.Config;

public class AirbrushTool extends AbstractTool {

    private static final boolean USE_TIMER = Config.getConfig().getToolsAirbrushUseTimer();
    private static final int PIXELS_PER_TICK = Config.getConfig().getToolsAirbrushPixelsPerTick();

    @Override
    public String getName() {
        return "Airbrush";
    }

    @Override
    public String getIcon() {
        return "tool_8.png";
    }

    @Override
    public boolean usesTimer() {
        return USE_TIMER;
    }

    @Override
    public void mousePressed(Graphics2D graphics2d, MouseEvent e) {
        super.mousePressed(graphics2d, e);
        if (!USE_TIMER) {
            drawAirbrush();
        }
    }

    @Override
    public void mouseDragged(Graphics2D graphics2d, MouseEvent e) {
        super.mouseDragged(graphics2d, e);
        if (!USE_TIMER) {
            drawAirbrush();
        }
    }

    @Override
    public void mouseReleased(Graphics2D graphics2d, MouseEvent e) {
        super.mouseReleased(graphics2d, e);
        if (!USE_TIMER) {
            drawAirbrush();
        }
    }

    @Override
    public void timerEvent(Graphics2D graphics2d, ActionEvent e) {
        for (int i = 0; i < PIXELS_PER_TICK; i++) {
            drawAirbrush();
        }
    }

    private void drawAirbrush() {
        final double r = toolState.getAirbrushSize() * Math.random();
        final double a = 2 * Math.PI * Math.random();
        final int lx = x + (int) (r * Math.sin(a));
        final int ly = y + (int) (r * Math.cos(a));
        final BufferedImage image = imageState.getImage();
        if (0 <= lx && lx < image.getWidth()
         && 0 <= ly && ly < image.getHeight()) {
            final int rgb;
            switch (toolState.getAirbrushType()) {
            case NORMAL:
                rgb = toolState.getColorPrimary().getRGB();
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
