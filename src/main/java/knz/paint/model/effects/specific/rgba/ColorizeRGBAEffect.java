package knz.paint.model.effects.specific.rgba;

import java.awt.Color;

import knz.paint.model.effects.parameter.ColorParameter;

public class ColorizeRGBAEffect extends AbstractRGBAEffect {

    private ColorParameter paramDark   = new ColorParameter("Dark",   Color.BLACK);
    private ColorParameter paramBright = new ColorParameter("Bright", Color.WHITE);

    public ColorizeRGBAEffect() {
        super("Colorize", true, true);
        this.parameters.add(paramDark);
        this.parameters.add(paramBright);
    }

    @Override
    protected void filter(int in_r, int in_g, int in_b, int in_a) {
        final Color dark   = paramDark.getValue();
        final Color bright = paramBright.getValue();
        final double gray = (in_r + in_g + in_b) / (double) (3 * 0xFF);
        final double negGray = 1 - gray;
        out_r = (int) (negGray * dark.getRed()   + gray * bright.getRed());
        out_g = (int) (negGray * dark.getGreen() + gray * bright.getGreen());
        out_b = (int) (negGray * dark.getBlue()  + gray * bright.getBlue());
        out_a = (int) (negGray * dark.getAlpha() + gray * bright.getAlpha());
    }

}
