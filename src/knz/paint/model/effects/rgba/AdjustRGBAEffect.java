package knz.paint.model.effects.rgba;

import knz.paint.model.effects.BooleanParameter;
import knz.paint.model.effects.DoubleParameter;

public class AdjustRGBAEffect extends RGBAEffect {

    private BooleanParameter paramInvertRGB = new BooleanParameter("Invert RGB controls", true);
    private DoubleParameter paramRed   = new DoubleParameter("Red",   0, 1, 2);
    private DoubleParameter paramGreen = new DoubleParameter("Green", 0, 1, 2);
    private DoubleParameter paramBlue  = new DoubleParameter("Blue",  0, 1, 2);
    private BooleanParameter paramInvertAlpha = new BooleanParameter("Invert Alpha control", false);
    private DoubleParameter paramAlpha = new DoubleParameter("Alpha", 0, 1, 2);

    public AdjustRGBAEffect() {
        super("Adjust RGBA", false, true);
        this.parameters.add(paramInvertRGB);
        this.parameters.add(paramRed);
        this.parameters.add(paramGreen);
        this.parameters.add(paramBlue);
        this.parameters.add(paramInvertAlpha);
        this.parameters.add(paramAlpha);
    }

    @Override
    public void filter(int x, int y, int in_r, int in_g, int in_b, int in_a) {
        final boolean invertRGB = paramInvertRGB.getValue();
        final double red   = paramRed.getValue();
        final double green = paramGreen.getValue();
        final double blue  = paramBlue.getValue();
        final boolean invertAlpha = paramInvertAlpha.getValue();
        final double alpha = paramAlpha.getValue();
        if (paramRed.isSet()) {
            out_r = (int) (0xFF * (float) Math.pow(in_r / (double) 0xFF, invertRGB   ? 1 / red   : red));
        }
        if (paramGreen.isSet()) {
            out_g = (int) (0xFF * (float) Math.pow(in_g / (double) 0xFF, invertRGB   ? 1 / green : green));
        }
        if (paramBlue.isSet()) {
            out_b = (int) (0xFF * (float) Math.pow(in_b / (double) 0xFF, invertRGB   ? 1 / blue  : blue));
        }
        if (paramAlpha.isSet()) {
            out_a = (int) (0xFF * (float) Math.pow(in_a / (double) 0xFF, invertAlpha ? 1 / alpha : alpha));
        }
    }

}
