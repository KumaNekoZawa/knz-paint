package knz.paint.model.effects.specific.hsba;

import java.awt.Color;

import knz.paint.model.effects.parameter.ColorParameter;

public class ColorizeHSBAEffect extends AbstractHSBAEffect {

    private ColorParameter paramDark   = new ColorParameter("Dark",   Color.BLACK);
    private ColorParameter paramBright = new ColorParameter("Bright", Color.WHITE);

    private float[] hsbDark, hsbBright;

    public ColorizeHSBAEffect() {
        super("Colorize", true, true);
        this.parameters.add(paramDark);
        this.parameters.add(paramBright);
    }

    @Override
    protected void applyHead() {
        final Color dark   = paramDark.getValue();
        final Color bright = paramBright.getValue();
        hsbDark   = Color.RGBtoHSB(dark.getRed(),   dark.getGreen(),   dark.getBlue(),   null);
        hsbBright = Color.RGBtoHSB(bright.getRed(), bright.getGreen(), bright.getBlue(), null);
    }

    @Override
    protected void filter(float in_h, float in_s, float in_b, int in_a) {
        final Color dark   = paramDark.getValue();
        final Color bright = paramBright.getValue();
        final float neg = 1f - in_b;
        out_h =        neg * hsbDark[0]      + in_b * hsbBright[0];
        out_s =        neg * hsbDark[1]      + in_b * hsbBright[1];
        out_b =        neg * hsbDark[2]      + in_b * hsbBright[2];
        out_a = (int) (neg * dark.getAlpha() + in_b * bright.getAlpha());
    }

}
