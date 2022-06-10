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
    private DoubleParameter paramAmountHue        = new DoubleParameter("Amount hue",        0, 0, 1);
    private DoubleParameter paramHue              = new DoubleParameter("Hue", "°", MIN_ANGLE, 0, MAX_ANGLE);
    private DoubleParameter paramAmountSaturation = new DoubleParameter("Amount saturation", 0, 0, 1);
    private DoubleParameter paramSaturation       = new DoubleParameter("Saturation", 0, 1, 1);
    private DoubleParameter paramAmountBrightness = new DoubleParameter("Amount brightness", 0, 0, 1);
    private DoubleParameter paramBrightness       = new DoubleParameter("Brightness", 0, 1, 1);

    public MixHSBEffect() {
        super("Mix HSB");
        this.parameters.add(paramPresets);
        this.parameters.add(paramAmountHue);
        this.parameters.add(paramHue);
        this.parameters.add(paramAmountSaturation);
        this.parameters.add(paramSaturation);
        this.parameters.add(paramAmountBrightness);
        this.parameters.add(paramBrightness);
    }

    @Override
    protected void filter(float in_h, float in_s, float in_b, int in_a) {
        final double amountHue        = paramAmountHue.getValue();
        final double hue              = paramHue.getValue() / 360;
        final double amountSaturation = paramAmountSaturation.getValue();
        final double saturation       = paramSaturation.getValue();
        final double amountBrightness = paramAmountBrightness.getValue();
        final double brightness       = paramBrightness.getValue();
        final double negAmountHue        = 1 - amountHue;
        final double negAmountSaturation = 1 - amountSaturation;
        final double negAmountBrightness = 1 - amountBrightness;
        if (paramAmountHue.isSet() || paramHue.isSet()) {
            out_h = (float) (negAmountHue * in_h + amountHue * hue);
        }
        if (paramAmountSaturation.isSet() || paramSaturation.isSet()) {
            out_s = (float) (negAmountSaturation * in_s + amountSaturation * saturation);
        }
        if (paramAmountBrightness.isSet() || paramBrightness.isSet()) {
            out_b = (float) (negAmountBrightness * in_b + amountBrightness * brightness);
        }
    }

}
