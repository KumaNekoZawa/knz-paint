package knz.paint.model.effects.specific.other;

import java.awt.image.BufferedImage;

import knz.paint.model.effects.specific.AbstractEffect;

/* see: Beyond Photography: The Digital Darkroom p.54-55 */
public class ChessonEffect extends AbstractEffect {

    // FIXME maybe make this an XYRGBAEffect

    public ChessonEffect() {
        super("Other.Chesson effect");
    }

    @Override
    protected final BufferedImage applyBody(BufferedImage image) {
        final int width  = image.getWidth();
        final int height = image.getHeight();
        final BufferedImage result = new BufferedImage(width, height, image.getType());
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                final int in = image.getRGB(x, y);
                final int in_r = (in >> 16) & 0xFF;
                final int in_g = (in >>  8) & 0xFF;
                final int in_b =  in        & 0xFF;
                final int in_a = (in >> 24) & 0xFF;
                final int out_r = filter(x, y, in_r);
                final int out_g = filter(x, y, in_g);
                final int out_b = filter(x, y, in_b);
                final int out_a = filter(x, y, in_a);
                final int out = (out_a << 24)
                              | (out_r << 16)
                              | (out_g << 8)
                              |  out_b;
                result.setRGB(x, y, out);
            }
        }
        return result;
    }

    private static int filter(int x, int y, int in) {
        final int dx = x - 0x80;
        final int dy = y - 0x80;
        final int factor = 0x80 - dx * dx - dy * dy;
        return (in ^ (factor * in) >>> 17) % 0x100;
    }

}
