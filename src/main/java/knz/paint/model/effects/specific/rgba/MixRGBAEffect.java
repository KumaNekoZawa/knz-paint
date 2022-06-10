package knz.paint.model.effects.specific.rgba;

import java.awt.Color;

import knz.paint.model.effects.parameter.ColorParameter;
import knz.paint.model.effects.parameter.DoubleParameter;
import knz.paint.model.effects.parameter.Preset;
import knz.paint.model.effects.parameter.PresetParameter;

public class MixRGBAEffect extends AbstractRGBAEffect {

    private PresetParameter paramPresets = new PresetParameter(
        new String[] { "Amount", "Color" },
        new Preset("Default", 0,    Color.BLACK),
        new Preset("Sepia",   0.75, new Color(0xB3, 0x9F, 0x77))
    );
    private DoubleParameter paramAmount = new DoubleParameter("Amount", 0, 0, 1);
    private ColorParameter  paramColor  = new ColorParameter("Color", Color.BLACK);

    public MixRGBAEffect() {
        super("Mix RGBA", true, true);
        this.parameters.add(paramPresets);
        this.parameters.add(paramAmount);
        this.parameters.add(paramColor);
    }

    @Override
    protected void filter(int in_r, int in_g, int in_b, int in_a) {
        final double amount = paramAmount.getValue();
        final Color  color  = paramColor.getValue();
        final double negAmount = 1 - amount;
        out_r = (int) (negAmount * in_r + amount * color.getRed());
        out_g = (int) (negAmount * in_g + amount * color.getGreen());
        out_b = (int) (negAmount * in_b + amount * color.getBlue());
        out_a = (int) (negAmount * in_a + amount * color.getAlpha());
    }

}
