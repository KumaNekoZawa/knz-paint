package knz.paint.model.effects.specific.xycolor;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import knz.paint.model.effects.specific.AbstractEffect;

public abstract class AbstractXYToColorEffect extends AbstractEffect {

    protected boolean out_affected;
    protected Color out_color;

    public AbstractXYToColorEffect(String name) {
        super("XY to color." + name);
    }

    @Override
    protected final void applyHead(BufferedImage image) {
        applyHead();
    }

    protected void applyHead() {
    }

    @Override
    protected final BufferedImage applyBody(BufferedImage image) {
        final int width  = image.getWidth();
        final int height = image.getHeight();
        final BufferedImage result = new BufferedImage(width, height, image.getType());
        final Graphics2D graphics2d = result.createGraphics();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                final int in = image.getRGB(x, y);
                out_affected = false;
                filter(width, height, x, y);
                result.setRGB(x, y, in);
                if (out_affected) {
                    graphics2d.setColor(out_color);
                    graphics2d.drawLine(x, y, x, y);
                }
            }
        }
        graphics2d.dispose();
        return result;
    }

    @Override
    protected final void applyFoot(BufferedImage image) {
        applyFoot();
    }

    protected void applyFoot() {
    }

    protected abstract void filter(int width, int height, int x, int y);

}
