package knz.paint.model.effects.specific.positional;

import java.awt.image.BufferedImage;

import knz.paint.model.effects.specific.AbstractEffect;

public abstract class AbstractPositionalEffect extends AbstractEffect {

    protected int fromX, fromY;

    public AbstractPositionalEffect(String name) {
        super(name);
    }

    @Override
    protected final void applyHead(BufferedImage image) {
        applyHead(image.getWidth(), image.getHeight());
    }

    protected void applyHead(int width, int height) {
    }

    @Override
    protected final BufferedImage applyBody(BufferedImage image) {
        final int width  = image.getWidth();
        final int height = image.getHeight();
        final BufferedImage result = new BufferedImage(width, height, image.getType());
        for (int toY = 0; toY < height; toY++) {
            for (int toX = 0; toX < width; toX++) {
                fromX = toX;
                fromY = toY;
                filter(width, height, toX, toY);
                fromX = Math.max(0, Math.min(width  - 1, fromX));
                fromY = Math.max(0, Math.min(height - 1, fromY));
                result.setRGB(toX, toY, image.getRGB(fromX, fromY));
            }
        }
        return result;
    }

    @Override
    protected final void applyFoot(BufferedImage image) {
        applyFoot(image.getWidth(), image.getHeight());
    }

    protected void applyFoot(int width, int height) {
    }

    protected abstract void filter(int width, int height, int toX, int toY);

}
