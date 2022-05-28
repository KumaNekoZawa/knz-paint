package knz.paint.model.effects.rgba;

import java.awt.image.BufferedImage;

import knz.paint.model.effects.Effect;

public abstract class RGBAEffect extends Effect {

    protected int x, y, r, g, b, a;

    public RGBAEffect(String name) {
        super(name);
    }

    @Override
    public BufferedImage apply(BufferedImage image) {
        final int width = image.getWidth();
        final int height = image.getHeight();
        BufferedImage result = new BufferedImage(width, height, image.getType());
        for (y = 0; y < height; y++) {
            for (x = 0; x < width; x++) {
                final int in = image.getRGB(x, y);
                r = (in >> 16) & 0xFF;
                g = (in >> 8) & 0xFF;
                b = in & 0xFF;
                a = (in >> 24) & 0xFF;
                filter();
                r = Math.max(0x00, Math.min(0xFF, r));
                g = Math.max(0x00, Math.min(0xFF, g));
                b = Math.max(0x00, Math.min(0xFF, b));
                a = Math.max(0x00, Math.min(0xFF, a));
                final int out = (a << 24) | (r << 16) | (g << 8) | b;
                result.setRGB(x, y, out);
            }
        }
        return result;
    }

    protected abstract void filter();

}
