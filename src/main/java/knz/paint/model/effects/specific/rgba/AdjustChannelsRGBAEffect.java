package knz.paint.model.effects.specific.rgba;

import knz.paint.model.effects.parameter.BooleanParameter;
import knz.paint.model.effects.parameter.DoubleParameter;

public class AdjustChannelsRGBAEffect extends AbstractRGBAEffect {

    private BooleanParameter paramInvertRGB = new BooleanParameter("Invert RGB controls", true);
    private DoubleParameter  paramR         = new DoubleParameter("Red",   0, 1, MAX_EXPONENT);
    private DoubleParameter  paramG         = new DoubleParameter("Green", 0, 1, MAX_EXPONENT);
    private DoubleParameter  paramB         = new DoubleParameter("Blue",  0, 1, MAX_EXPONENT);
    private BooleanParameter paramInvertA   = new BooleanParameter("Invert Alpha control", false);
    private DoubleParameter  paramA         = new DoubleParameter("Alpha", 0, 1, MAX_EXPONENT);

    public AdjustChannelsRGBAEffect() {
        super("Adjust channels", false, true);
        this.parameters.add(paramInvertRGB);
        this.parameters.add(paramR);
        this.parameters.add(paramG);
        this.parameters.add(paramB);
        this.parameters.add(paramInvertA);
        this.parameters.add(paramA);
    }

    @Override
    protected void filter(int in_r, int in_g, int in_b, int in_a) {
        final boolean invertRGB = paramInvertRGB.getValue();
        final double r = paramR.getValue();
        final double g = paramG.getValue();
        final double b = paramB.getValue();
        final boolean invertA = paramInvertA.getValue();
        final double a = paramA.getValue();
        out_r = paramR.isSet() ? (int) (Math.pow(in_r / (double) 0xFF, invertRGB ? 1 / r : r) * 0xFF) : in_r;
        out_g = paramG.isSet() ? (int) (Math.pow(in_g / (double) 0xFF, invertRGB ? 1 / g : g) * 0xFF) : in_g;
        out_b = paramB.isSet() ? (int) (Math.pow(in_b / (double) 0xFF, invertRGB ? 1 / b : b) * 0xFF) : in_b;
        out_a = paramA.isSet() ? (int) (Math.pow(in_a / (double) 0xFF, invertA   ? 1 / a : a) * 0xFF) : in_a;
    }

}
