package knz.paint.model.effects.specific.hsba;

import java.awt.Color;
import java.awt.image.BufferedImage;

import knz.paint.model.effects.parameter.BooleanParameter;
import knz.paint.model.effects.specific.AbstractEffect;

public abstract class AbstractHSBAEffect extends AbstractEffect {

    private BooleanParameter paramH, paramS, paramB, paramA;
    private boolean affectsAlpha;

    protected float out_h, out_s, out_b;
    protected int out_a;

    public AbstractHSBAEffect(String name, boolean hasDefaultParameters, boolean affectsAlpha) {
        super("HSBA." + name);
        this.affectsAlpha = affectsAlpha;
        if (hasDefaultParameters) {
            this.paramH = new BooleanParameter("Affect hue",        true);
            this.paramS = new BooleanParameter("Affect saturation", true);
            this.paramB = new BooleanParameter("Affect brightness", true);
            this.paramA = new BooleanParameter("Affect alpha",      false);
            this.parameters.add(paramH);
            this.parameters.add(paramS);
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
        final boolean h = paramH != null ? paramH.getValue() : true;
        final boolean s = paramS != null ? paramS.getValue() : true;
        final boolean b = paramB != null ? paramB.getValue() : true;
        final boolean a = paramA != null ? paramA.getValue() : affectsAlpha;
        final int width  = image.getWidth();
        final int height = image.getHeight();
        final BufferedImage result = new BufferedImage(width, height, image.getType());
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                final int in = image.getRGB(x, y);
                final float[] in_hsb = Color.RGBtoHSB(
                    (in >> 16) & 0xFF,
                    (in >> 8)  & 0xFF,
                     in        & 0xFF,
                null);
                final float in_h = in_hsb[0];
                final float in_s = in_hsb[1];
                final float in_b = in_hsb[2];
                final int   in_a = (in >> 24) & 0xFF;
                out_h = in_h;
                out_s = in_s;
                out_b = in_b;
                out_a = in_a;
                filter(in_h, in_s, in_b, in_a);
                out_h = h ?                               out_h   : in_h;
                out_s = s ? Math.max(0,    Math.min(1,    out_s)) : in_s;
                out_b = b ? Math.max(0,    Math.min(1,    out_b)) : in_b;
                out_a = a ? Math.max(0x00, Math.min(0xFF, out_a)) : in_a;
                final int out = (out_a << 24) | (Color.HSBtoRGB(out_h, out_s, out_b) & 0xFFFFFF);
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

    protected abstract void filter(float in_h, float in_s, float in_b, int in_a);

}
