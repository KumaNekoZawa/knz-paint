package knz.paint.model.effects.specific.hsba;

import knz.paint.model.effects.parameter.BooleanParameter;
import knz.paint.model.effects.parameter.DoubleParameter;

public class AdjustHSBAEffect extends AbstractHSBAEffect {

    private DoubleParameter  paramHuePhase    = new DoubleParameter("Hue phase", -180, 0, 180);
    private BooleanParameter paramInvertSB    = new BooleanParameter("Invert Sat./Br. controls", true);
    private DoubleParameter  paramSaturation  = new DoubleParameter("Saturation", 0, 1, 2);
    private DoubleParameter  paramBrightness  = new DoubleParameter("Brightness", 0, 1, 2);
    private BooleanParameter paramInvertAlpha = new BooleanParameter("Invert Alpha control", false);
    private DoubleParameter  paramAlpha       = new DoubleParameter("Alpha", 0, 1, 2);

    public AdjustHSBAEffect() {
        super("Adjust HSBA");
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
        if (paramHuePhase.isSet()) {
            out_h = in_h + (float) huePhase;
        }
        if (paramSaturation.isSet()) {
            out_s = (float) Math.pow(in_s, invertSB ? 1 / saturation : saturation);
        }
        if (paramBrightness.isSet()) {
            out_b = (float) Math.pow(in_b, invertSB ? 1 / brightness : brightness);
        }
        if (paramAlpha.isSet()) {
            out_a = (int) (0xFF * (float) Math.pow(in_a / (double) 0xFF, invertAlpha ? 1 / alpha : alpha));
        }
    }

}
