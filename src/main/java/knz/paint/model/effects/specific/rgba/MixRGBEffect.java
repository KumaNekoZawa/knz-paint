package knz.paint.model.effects.specific.rgba;

import knz.paint.model.effects.parameter.DoubleParameter;
import knz.paint.model.effects.parameter.IntegerParameter;
import knz.paint.model.effects.parameter.Preset;
import knz.paint.model.effects.parameter.PresetParameter;

public class MixRGBEffect extends AbstractRGBAEffect {

    private PresetParameter paramPresets = new PresetParameter(
        new String[] { "Amount", "Red", "Green", "Blue" },
        new Preset("Default", 0,    0x80, 0x80, 0x80),
        new Preset("Sepia",   0.75, 0xB3, 0x9F, 0x77)
    );
    private DoubleParameter  paramAmount = new DoubleParameter("Amount", 0, 0, 1);
    private IntegerParameter paramRed    = new IntegerParameter("Red",   0x00, 0x80, 0xFF);
    private IntegerParameter paramGreen  = new IntegerParameter("Green", 0x00, 0x80, 0xFF);
    private IntegerParameter paramBlue   = new IntegerParameter("Blue",  0x00, 0x80, 0xFF);

    public MixRGBEffect() {
        super("Mix RGB", false, false);
        this.parameters.add(paramPresets);
        this.parameters.add(paramAmount);
        this.parameters.add(paramRed);
        this.parameters.add(paramGreen);
        this.parameters.add(paramBlue);
    }

    @Override
    protected void filter(int in_r, int in_g, int in_b, int in_a) {
        final double amount = paramAmount.getValue();
        final int    red    = paramRed.getValue();
        final int    green  = paramGreen.getValue();
        final int    blue   = paramBlue.getValue();
        final double negAmount = 1 - amount;
        out_r = (int) (negAmount * in_r + amount * red);
        out_g = (int) (negAmount * in_g + amount * green);
        out_b = (int) (negAmount * in_b + amount * blue);
    }

}
