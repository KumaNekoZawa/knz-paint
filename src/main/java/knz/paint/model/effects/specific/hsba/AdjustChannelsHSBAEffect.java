package knz.paint.model.effects.specific.hsba;

import knz.paint.model.effects.parameter.BooleanParameter;
import knz.paint.model.effects.parameter.DoubleParameter;

public class AdjustChannelsHSBAEffect extends AbstractHSBAEffect {

    private DoubleParameter  paramHuePhase    = new DoubleParameter("Hue phase", "°", MIN_ANGLE, 0, MAX_ANGLE);
    private BooleanParameter paramInvertSB    = new BooleanParameter("Invert Sat./Br. controls", true);
    private DoubleParameter  paramSaturation  = new DoubleParameter("Saturation", 0, 1, MAX_EXPONENT);
    private DoubleParameter  paramBrightness  = new DoubleParameter("Brightness", 0, 1, MAX_EXPONENT);
    private BooleanParameter paramInvertAlpha = new BooleanParameter("Invert Alpha control", false);
    private DoubleParameter  paramAlpha       = new DoubleParameter("Alpha",      0, 1, MAX_EXPONENT);

    public AdjustChannelsHSBAEffect() {
        super("Adjust channels");
        this.parameters.add(paramHuePhase);
        this.parameters.add(paramInvertSB);
        this.parameters.add(paramSaturation);
        this.parameters.add(paramBrightness);
        this.parameters.add(paramInvertAlpha);
        this.parameters.add(paramAlpha);
    }

    @Override
    protected void filter(float in_h, float in_s, float in_b, int in_a) {
        final double  huePhase    = paramHuePhase.getValue() / 360;
        final boolean invertSB    = paramInvertSB.getValue();
        final double  saturation  = paramSaturation.getValue();
        final double  brightness  = paramBrightness.getValue();
        final boolean invertAlpha = paramInvertAlpha.getValue();
        final double  alpha       = paramAlpha.getValue();
        out_h = paramHuePhase.isSet()   ? in_h + (float) huePhase                                                              : in_h;
        out_s = paramSaturation.isSet() ? (float) Math.pow(in_s,                 invertSB    ? 1 / saturation : saturation)    : in_s;
        out_b = paramBrightness.isSet() ? (float) Math.pow(in_b,                 invertSB    ? 1 / brightness : brightness)    : in_b;
        out_a = paramAlpha.isSet()      ?  (int) (Math.pow(in_a / (double) 0xFF, invertAlpha ? 1 / alpha      : alpha) * 0xFF) : in_a;
    }

}
