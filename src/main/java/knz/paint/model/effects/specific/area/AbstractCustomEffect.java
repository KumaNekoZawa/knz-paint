package knz.paint.model.effects.specific.area;

import java.awt.image.BufferedImage;

import knz.paint.model.effects.parameter.BooleanParameter;
import knz.paint.model.effects.parameter.IntegerArrayParameter;
import knz.paint.model.effects.parameter.PresetParameter;
import knz.paint.model.effects.specific.AbstractEffect;

public abstract class AbstractCustomEffect extends AbstractEffect {

    private PresetParameter       paramPresets;
    private BooleanParameter      paramR     = new BooleanParameter("Affect red",   true);
    private BooleanParameter      paramG     = new BooleanParameter("Affect green", true);
    private BooleanParameter      paramB     = new BooleanParameter("Affect blue",  true);
    private BooleanParameter      paramA     = new BooleanParameter("Affect alpha", false);
    private BooleanParameter      paramScale = new BooleanParameter("Auto scale", true);
    private IntegerArrayParameter paramMatrix;

    private int areaSize;

    public AbstractCustomEffect(String name, int areaSize, PresetParameter paramPresets, IntegerArrayParameter paramMatrix) {
        super("Area.Custom." + name);
        this.paramPresets = paramPresets;
        this.paramMatrix = paramMatrix;
        this.areaSize = areaSize;
        this.parameters.add(paramPresets);
        this.parameters.add(paramR);
        this.parameters.add(paramG);
        this.parameters.add(paramB);
        this.parameters.add(paramA);
        this.parameters.add(paramScale);
        this.parameters.add(paramMatrix);
    }

    @Override
    protected final void applyHead(BufferedImage image) {
        applyHead();
    }

    protected void applyHead() {
    }

    @Override
    protected final BufferedImage applyBody(BufferedImage image) {
        final boolean r      = paramR.getValue();
        final boolean g      = paramG.getValue();
        final boolean b      = paramB.getValue();
        final boolean a      = paramA.getValue();
        final boolean scale  = paramScale.getValue();
        final int[]   matrix = paramMatrix.getValues();
        final int width  = image.getWidth();
        final int height = image.getHeight();
        final BufferedImage result = new BufferedImage(width, height, image.getType());
        final int matrixSize = 2 * areaSize + 1;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                final int in = image.getRGB(x, y);
                final int in_r = (in >> 16) & 0xFF;
                final int in_g = (in >>  8) & 0xFF;
                final int in_b =  in        & 0xFF;
                final int in_a = (in >> 24) & 0xFF;
                long min = 0, max = 0;
                long val_r = 0, val_g = 0, val_b = 0, val_a = 0;
                for (int dy = Math.max(0, y - areaSize); dy <= Math.min(height - 1, y + areaSize); dy++) {
                    for (int dx = Math.max(0, x - areaSize); dx <= Math.min(width - 1, x + areaSize); dx++) {
                        final int m = matrix[(dy - y + areaSize) * matrixSize + (dx - x + areaSize)];
                        if (m != 0) {
                            final int din = dx == x && dy == y ? in : image.getRGB(dx, dy);
                            final int din_r = (din >> 16) & 0xFF;
                            final int din_g = (din >>  8) & 0xFF;
                            final int din_b =  din        & 0xFF;
                            final int din_a = (din >> 24) & 0xFF;
                            if (m > 0) {
                                max += m;
                            } else if (m < 0) {
                                min += m;
                            }
                            val_r += m * din_r;
                            val_g += m * din_g;
                            val_b += m * din_b;
                            val_a += m * din_a;
                        }
                    }
                }
                if (scale) {
                    if (min == 0 && max == 0) {
                        result.setRGB(x, y, in);
                        continue;
                    }
                    val_r -= min * 0xFF;
                    val_g -= min * 0xFF;
                    val_b -= min * 0xFF;
                    val_a -= min * 0xFF;
                    final long divisor = max - min;
                    if (divisor != 0) {
                        val_r /= divisor;
                        val_g /= divisor;
                        val_b /= divisor;
                        val_a /= divisor;
                    }
                }
                final int out_r = r ? Math.max(0x00, Math.min(0xFF, (int) val_r)) : in_r;
                final int out_g = g ? Math.max(0x00, Math.min(0xFF, (int) val_g)) : in_g;
                final int out_b = b ? Math.max(0x00, Math.min(0xFF, (int) val_b)) : in_b;
                final int out_a = a ? Math.max(0x00, Math.min(0xFF, (int) val_a)) : in_a;
                final int out = (out_a << 24)
                              | (out_r << 16)
                              | (out_g << 8)
                              |  out_b;
                result.setRGB(x, y, out);
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
