package knz.paint.model.effects.rgba;

import knz.paint.model.effects.DoubleParameter;

// XXX this is a special case of the AdjustRGBAEffect where RGBA are linked and the controls not inverted
public class AdjustGammaEffect extends RGBAEffect {

    private DoubleParameter paramExponent = new DoubleParameter("Exponent", 0, 1, 2);

    public AdjustGammaEffect() {
        super("Adjust gamma", true, true);
        this.parameters.add(paramExponent);
    }

    @Override
    public void filter(int x, int y, int in_r, int in_g, int in_b, int in_a) {
        final double exponent = paramExponent.getValue();
        out_r = (int) (0xFF * Math.pow(in_r / (double) 0xFF, exponent));
        out_g = (int) (0xFF * Math.pow(in_g / (double) 0xFF, exponent));
        out_b = (int) (0xFF * Math.pow(in_b / (double) 0xFF, exponent));
        out_a = (int) (0xFF * Math.pow(in_a / (double) 0xFF, exponent));
    }

}
