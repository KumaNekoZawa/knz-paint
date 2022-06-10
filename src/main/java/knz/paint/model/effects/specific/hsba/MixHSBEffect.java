package knz.paint.model.effects.specific.hsba;

import knz.paint.model.effects.parameter.DoubleParameter;
import knz.paint.model.effects.parameter.Preset;
import knz.paint.model.effects.parameter.PresetParameter;

public class MixHSBEffect extends AbstractHSBAEffect {

    private PresetParameter paramPresets = new PresetParameter(
        new String[] { "Amount hue", "Hue", "Amount saturation", "Saturation", "Amount brightness", "Brightness" },
        new Preset("Default", 0,    0,    0,    1,    0, 1),
        new Preset("Sepia",   0.75, 0.11, 0.05, 0.39, 0, 1)
    );
    private DoubleParameter paramAmountH    = new DoubleParameter("Amount hue",        0, 0, 1);
    private DoubleParameter paramHue        = new DoubleParameter("Hue", "°", MIN_ANGLE, 0, MAX_ANGLE);
    private DoubleParameter paramAmountS    = new DoubleParameter("Amount saturation", 0, 0, 1);
    private DoubleParameter paramSaturation = new DoubleParameter("Saturation", 0, 1, 1);
    private DoubleParameter paramAmountB    = new DoubleParameter("Amount brightness", 0, 0, 1);
    private DoubleParameter paramBrightness = new DoubleParameter("Brightness", 0, 1, 1);

    public MixHSBEffect() {
        super("Mix HSB");
        this.parameters.add(paramPresets);
        this.parameters.add(paramAmountH);
        this.parameters.add(paramHue);
        this.parameters.add(paramAmountS);
        this.parameters.add(paramSaturation);
        this.parameters.add(paramAmountB);
        this.parameters.add(paramBrightness);
    }

    @Override
    protected void filter(float in_h, float in_s, float in_b, int in_a) {
        final double amountH    = paramAmountH.getValue();
        final double hue        = paramHue.getValue() / 360;
        final double amountS    = paramAmountS.getValue();
        final double saturation = paramSaturation.getValue();
        final double amountB    = paramAmountB.getValue();
        final double brightness = paramBrightness.getValue();
        final double negAmountH = 1 - amountH;
        final double negAmountS = 1 - amountS;
        final double negAmountB = 1 - amountB;
        if (paramAmountH.isSet() || paramHue.isSet()) {
            out_h = (float) (negAmountH * in_h + amountH * hue);
        }
        if (paramAmountS.isSet() || paramSaturation.isSet()) {
            out_s = (float) (negAmountS * in_s + amountS * saturation);
        }
        if (paramAmountB.isSet() || paramBrightness.isSet()) {
            out_b = (float) (negAmountB * in_b + amountB * brightness);
        }
    }

}
