package knz.paint.model.effects.rgba;

import java.awt.image.BufferedImage;

import knz.paint.model.effects.AbstractEffect;
import knz.paint.model.effects.BooleanParameter;

public abstract class AbstractRGBAEffect extends AbstractEffect {

    private boolean showDefaultParameters;
    private boolean affectsAlpha;
    private BooleanParameter paramRed   = new BooleanParameter("Affect red",   true);
    private BooleanParameter paramGreen = new BooleanParameter("Affect green", true);
    private BooleanParameter paramBlue  = new BooleanParameter("Affect blue",  true);
    private BooleanParameter paramAlpha = new BooleanParameter("Affect alpha", false);

    protected int out_r, out_g, out_b, out_a;

    public AbstractRGBAEffect(String name, boolean showDefaultParameters, boolean affectsAlpha) {
        super(name);
        this.showDefaultParameters = showDefaultParameters;
        this.affectsAlpha = affectsAlpha;
        if (showDefaultParameters) {
            this.parameters.add(paramRed);
            this.parameters.add(paramGreen);
            this.parameters.add(paramBlue);
            if (affectsAlpha) {
                this.parameters.add(paramAlpha);
            }
        }
    }

    @Override
    public BufferedImage applyHelper(BufferedImage image) {
        final int width  = image.getWidth();
        final int height = image.getHeight();
        final BufferedImage result = new BufferedImage(width, height, image.getType());
        final boolean red   = paramRed.getValue();
        final boolean green = paramGreen.getValue();
        final boolean blue  = paramBlue.getValue();
        final boolean alpha = paramAlpha.getValue();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                final int in = image.getRGB(x, y);
                final int in_r = (in >> 16) & 0xFF;
                final int in_g = (in >>  8) & 0xFF;
                final int in_b =  in        & 0xFF;
                final int in_a = (in >> 24) & 0xFF;
                out_r = in_r;
                out_g = in_g;
                out_b = in_b;
                out_a = in_a;
                filter(x, y, in_r, in_g, in_b, in_a);
                out_r =                  !showDefaultParameters || red    ? Math.max(0x00, Math.min(0xFF, out_r)) : in_r;
                out_g =                  !showDefaultParameters || green  ? Math.max(0x00, Math.min(0xFF, out_g)) : in_g;
                out_b =                  !showDefaultParameters || blue   ? Math.max(0x00, Math.min(0xFF, out_b)) : in_b;
                out_a = affectsAlpha && (!showDefaultParameters || alpha) ? Math.max(0x00, Math.min(0xFF, out_a)) : in_a;
                final int out = (out_a << 24)
                              | (out_r << 16)
                              | (out_g << 8)
                              |  out_b;
                result.setRGB(x, y, out);
            }
        }
        return result;
    }

    protected abstract void filter(int x, int y, int in_r, int in_g, int in_b, int in_a);

}
