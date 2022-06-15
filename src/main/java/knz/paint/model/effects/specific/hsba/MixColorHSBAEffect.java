package knz.paint.model.effects.specific.hsba;

import java.awt.Color;

import knz.paint.model.effects.parameter.ColorParameter;
import knz.paint.model.effects.parameter.DoubleParameter;
import knz.paint.model.effects.parameter.Preset;
import knz.paint.model.effects.parameter.PresetParameter;

public class MixColorHSBAEffect extends AbstractHSBAEffect {

    private PresetParameter paramPresets = new PresetParameter(
        new String[] { "Amount hue", "Amount saturation", "Amount brightness", "Color" },
        new Preset("Default", 0,    0,    0, Color.BLACK),
        new Preset("Sepia",   0.75, 0.75, 0, new Color(0xB3, 0x9F, 0x77, 0xFF))
    );
    private DoubleParameter paramAmountH = new DoubleParameter("Amount hue",        0, 0, 1);
    private DoubleParameter paramAmountS = new DoubleParameter("Amount saturation", 0, 0, 1);
    private DoubleParameter paramAmountB = new DoubleParameter("Amount brightness", 0, 0, 1);
    private DoubleParameter paramAmountA = new DoubleParameter("Amount alpha",      0, 0, 1);
    private ColorParameter  paramColor   = new ColorParameter("Color", Color.BLACK);

    public MixColorHSBAEffect() {
        super("Mix color", false, true);
        this.parameters.add(paramPresets);
        this.parameters.add(paramAmountH);
        this.parameters.add(paramAmountS);
        this.parameters.add(paramAmountB);
        this.parameters.add(paramAmountA);
        this.parameters.add(paramColor);
    }

    @Override
    protected void filter(float in_h, float in_s, float in_b, int in_a) {
        final double amountH = paramAmountH.getValue();
        final double amountS = paramAmountS.getValue();
        final double amountB = paramAmountB.getValue();
        final double amountA = paramAmountA.getValue();
        final Color  color   = paramColor.getValue();
        final double negAmountH = 1 - amountH;
        final double negAmountS = 1 - amountS;
        final double negAmountB = 1 - amountB;
        final double negAmountA = 1 - amountA;
        final float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        out_h = paramAmountH.isSet() ? (float) (negAmountH * in_h + amountH * hsb[0])           : in_h;
        out_s = paramAmountS.isSet() ? (float) (negAmountS * in_s + amountS * hsb[1])           : in_s;
        out_b = paramAmountB.isSet() ? (float) (negAmountB * in_b + amountB * hsb[2])           : in_b;
        out_a = paramAmountA.isSet() ?   (int) (negAmountA * in_a + amountA * color.getAlpha()) : in_a;
    }

}
