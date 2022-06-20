package knz.paint.model.effects.specific.rgba;

import knz.paint.model.effects.parameter.IntegerParameter;

public class PassRGBAEffect extends AbstractRGBAEffect {

    private IntegerParameter paramMinThreshold = new IntegerParameter("Minimum threshold", 0x000, 0x000, 0x100);
    private IntegerParameter paramMinValue     = new IntegerParameter("Minimum value",     0x00,  0x00,  0xFF);
    private IntegerParameter paramMaxThreshold = new IntegerParameter("Maximum threshold", 0x000, 0x100, 0x100);
    private IntegerParameter paramMaxValue     = new IntegerParameter("Maximum value",     0x00,  0xFF,  0xFF);

    public PassRGBAEffect() {
        super("Pass", true, true);
        this.parameters.add(paramMinThreshold);
        this.parameters.add(paramMinValue);
        this.parameters.add(paramMaxThreshold);
        this.parameters.add(paramMaxValue);
    }

    @Override
    protected void filter(int in_r, int in_g, int in_b, int in_a) {
        final int minThreshold = paramMinThreshold.getValue();
        final int minValue     = paramMinValue.getValue();
        final int maxThreshold = paramMaxThreshold.getValue();
        final int maxValue     = paramMaxValue.getValue();
        out_r = in_r < minThreshold ? minValue : (in_r >= maxThreshold ? maxValue : in_r);
        out_g = in_g < minThreshold ? minValue : (in_g >= maxThreshold ? maxValue : in_g);
        out_b = in_b < minThreshold ? minValue : (in_b >= maxThreshold ? maxValue : in_b);
        out_a = in_a < minThreshold ? minValue : (in_a >= maxThreshold ? maxValue : in_a);
    }

}
