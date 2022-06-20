package knz.paint.model.effects.specific;

import java.awt.image.BufferedImage;

public class AncientMapEffect extends AbstractEffect {

    public AncientMapEffect() {
        super("Misc.Ancient map");
    }

    @Override
    protected final void applyHead(BufferedImage image) {
        applyHead();
    }

    protected void applyHead() {
    }

    @Override
    protected final BufferedImage applyBody(BufferedImage image) {
        final int[] lx = { 0, 1, 1 };
        final int[] ly = { 1, 1, 0 };
        final int width  = image.getWidth();
        final int height = image.getHeight();
        final BufferedImage result = new BufferedImage(width, height, image.getType());
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                result.setRGB(x, y, image.getRGB(x, y));
            }
        }
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                final int off_r, off_g, off_b;
                {
                    final int in = result.getRGB(x, y);
                    final int in_r = (in >> 16) & 0xFF;
                    final int in_g = (in >>  8) & 0xFF;
                    final int in_b =  in        & 0xFF;
                    final int gray = (in_r + in_g + in_b) / 3;
                    off_r = Math.abs(in_r - gray);
                    off_g = Math.abs(in_g - gray);
                    off_b = Math.abs(in_b - gray);
                }
                for (int i = 0; i < lx.length; i++) {
                    final int px = x + lx[i];
                    final int py = y + ly[i];
                    if (0 <= px && px < width
                     && 0 <= py && py < height) {
                        final int in = result.getRGB(px, py);
                        final int in_r = (in >> 16) & 0xFF;
                        final int in_g = (in >>  8) & 0xFF;
                        final int in_b =  in        & 0xFF;
                        final int in_a = (in >> 24) & 0xFF;
                        final int out_r = Math.min(0xFF, in_r + off_r);
                        final int out_g = Math.min(0xFF, in_g + off_g);
                        final int out_b = Math.min(0xFF, in_b + off_b);
                        final int out_a = in_a;
                        final int out = (out_a << 24)
                                      | (out_r << 16)
                                      | (out_g << 8)
                                      |  out_b;
                        result.setRGB(px, py, out);
                    }
                }
            }
        }
        return result;
    }

    @Override
    protected final void applyFoot(BufferedImage image) {
        applyFoot();
    }

    protected void applyFoot() {
    }

}
