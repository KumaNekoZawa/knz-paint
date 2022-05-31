package knz.paint.model.effects.hsba;

import java.awt.Color;
import java.awt.image.BufferedImage;

import knz.paint.model.effects.AbstractEffect;

public abstract class AbstractHSBAEffect extends AbstractEffect {

    protected float out_h, out_s, out_b;
    protected int out_a;

    public AbstractHSBAEffect(String name) {
        super(name);
    }

    @Override
    public BufferedImage applyHelper(BufferedImage image) {
        final int width = image.getWidth();
        final int height = image.getHeight();
        BufferedImage result = new BufferedImage(width, height, image.getType());
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                final int in = image.getRGB(x, y);
                final float[] in_hsb = Color.RGBtoHSB(
                    (in >> 16) & 0xFF,
                    (in >> 8) & 0xFF,
                    in & 0xFF, null);
                final float in_h = in_hsb[0];
                final float in_s = in_hsb[1];
                final float in_b = in_hsb[2];
                final int in_a = (in >> 24) & 0xFF;
                out_h = in_h;
                out_s = in_s;
                out_b = in_b;
                out_a = in_a;
                filter(x, y, in_h, in_s, in_b, in_a);
                out_a = Math.max(0x00, Math.min(0xFF, out_a));
                final int out = (out_a << 24) | (Color.HSBtoRGB(out_h, out_s, out_b) & 0xFFFFFF);
                result.setRGB(x, y, out);
            }
        }
        return result;
    }

    protected abstract void filter(int x, int y, float in_h, float in_s, float in_b, int in_a);

}
