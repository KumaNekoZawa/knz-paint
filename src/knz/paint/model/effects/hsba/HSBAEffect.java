package knz.paint.model.effects.hsba;

import java.awt.Color;
import java.awt.image.BufferedImage;

import knz.paint.model.effects.Effect;

public abstract class HSBAEffect extends Effect {

    protected int x, y, a;
    protected float h, s, b;

    public HSBAEffect(String name) {
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
                final float hsb[] = Color.RGBtoHSB(
                    (in >> 16) & 0xFF,
                    (in >> 8) & 0xFF,
                    in & 0xFF, null);
                h = hsb[0];
                s = hsb[1];
                b = hsb[2];
                a = (in >> 24) & 0xFF;
                filter();
                a = Math.max(0x00, Math.min(0xFF, a));
                final int out = (a << 24) | (Color.HSBtoRGB(h, s, b) & 0xFFFFFF);
                result.setRGB(x, y, out);
            }
        }
        return result;
    }

    protected abstract void filter();

}
