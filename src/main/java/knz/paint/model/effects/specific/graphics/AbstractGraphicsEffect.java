package knz.paint.model.effects.specific.graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import knz.paint.model.effects.specific.AbstractEffect;

public abstract class AbstractGraphicsEffect extends AbstractEffect {

    public AbstractGraphicsEffect(String name) {
        super("Graphics." + name);
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
        graphics2d.drawImage(image, 0, 0, null);
        filter(width, height, graphics2d);
        graphics2d.dispose();
        return result;
    }

    @Override
    protected final void applyFoot(BufferedImage image) {
        applyFoot();
    }

    protected void applyFoot() {
    }

    protected abstract void filter(int width, int height, Graphics2D graphics2d);

}
