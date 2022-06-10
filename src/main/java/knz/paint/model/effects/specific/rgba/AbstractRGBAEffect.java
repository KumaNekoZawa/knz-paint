package knz.paint.model.effects.specific.rgba;

import java.awt.image.BufferedImage;

import knz.paint.model.effects.parameter.BooleanParameter;
import knz.paint.model.effects.specific.AbstractEffect;

public abstract class AbstractRGBAEffect extends AbstractEffect {

    private boolean affectsAlpha;
    private BooleanParameter paramR = new BooleanParameter("Affect red",   true);
    private BooleanParameter paramG = new BooleanParameter("Affect green", true);
    private BooleanParameter paramB = new BooleanParameter("Affect blue",  true);
    private BooleanParameter paramA;

    protected int out_r, out_g, out_b, out_a;

    public AbstractRGBAEffect(String name, boolean showDefaultParameters, boolean affectsAlpha) {
        super("RGBA." + name);
        this.paramA = new BooleanParameter("Affect alpha", !showDefaultParameters && affectsAlpha);
        this.affectsAlpha = affectsAlpha;
        if (showDefaultParameters) {
            this.parameters.add(paramR);
            this.parameters.add(paramG);
            this.parameters.add(paramB);
            if (affectsAlpha) {
                this.parameters.add(paramA);
            }
        }
    }

    @Override
    protected final void applyHead(BufferedImage image) {
        applyHead();
    }

    protected void applyHead() {
    }

    @Override
    protected final BufferedImage applyBody(BufferedImage image) {
        final boolean r = paramR.getValue();
        final boolean g = paramG.getValue();
        final boolean b = paramB.getValue();
        final boolean a = paramA.getValue();
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
                out_r = in_r;
                out_g = in_g;
                out_b = in_b;
                out_a = in_a;
                filter(in_r, in_g, in_b, in_a);
                out_r = r ? Math.max(0x00, Math.min(0xFF, out_r)) : in_r;
                out_g = g ? Math.max(0x00, Math.min(0xFF, out_g)) : in_g;
                out_b = b ? Math.max(0x00, Math.min(0xFF, out_b)) : in_b;
                out_a = a ? Math.max(0x00, Math.min(0xFF, out_a)) : in_a;
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

    protected abstract void filter(int in_r, int in_g, int in_b, int in_a);

}
