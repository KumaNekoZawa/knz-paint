package knz.paint.model.effects.specific.rgba;

import knz.paint.model.effects.parameter.DoubleParameter;

// XXX this is a special case of the AdjustRGBAEffect where RGBA are linked and the controls not inverted
public class AdjustGammaEffect extends AbstractRGBAEffect {

    private DoubleParameter paramExponent = new DoubleParameter("Exponent", 0, 1, MAX_EXPONENT);

    public AdjustGammaEffect() {
        super("Adjust gamma", true, true);
        this.parameters.add(paramExponent);
    }

    @Override
    protected void filter(int in_r, int in_g, int in_b, int in_a) {
        final double exponent = paramExponent.getValue();
        out_r = (int) (Math.pow(in_r / (double) 0xFF, exponent) * 0xFF);
        out_g = (int) (Math.pow(in_g / (double) 0xFF, exponent) * 0xFF);
        out_b = (int) (Math.pow(in_b / (double) 0xFF, exponent) * 0xFF);
        out_a = (int) (Math.pow(in_a / (double) 0xFF, exponent) * 0xFF);
    }

}
