package knz.paint.model.effects.specific.hsba;

import knz.paint.model.effects.parameter.DoubleParameter;
import knz.paint.model.effects.parameter.IntegerParameter;

public class PassHSBAEffect extends AbstractHSBAEffect {

    private DoubleParameter  paramMinSThreshold = new DoubleParameter("Minimum saturation threshold", 0, 0, 1);
    private DoubleParameter  paramMinSValue     = new DoubleParameter("Minimum saturation value",     0, 0, 1);
    private DoubleParameter  paramMaxSThreshold = new DoubleParameter("Maximum saturation threshold", 0, 1, 1);
    private DoubleParameter  paramMaxSValue     = new DoubleParameter("Maximum saturation value",    0, 1, 1);
    private DoubleParameter  paramMinBThreshold = new DoubleParameter("Minimum brightness threshold", 0, 0, 1);
    private DoubleParameter  paramMinBValue     = new DoubleParameter("Minimum brightness value",     0, 0, 1);
    private DoubleParameter  paramMaxBThreshold = new DoubleParameter("Maximum brightness threshold", 0, 1, 1);
    private DoubleParameter  paramMaxBValue     = new DoubleParameter("Maximum brightness value",     0, 1, 1);
    private IntegerParameter paramMinAThreshold = new IntegerParameter("Minimum alpha threshold", 0x000, 0x000, 0x100);
    private IntegerParameter paramMinAValue     = new IntegerParameter("Minimum alpha value",     0x00,  0x00,  0xFF);
    private IntegerParameter paramMaxAThreshold = new IntegerParameter("Maximum alpha threshold", 0x000, 0x100, 0x100);
    private IntegerParameter paramMaxAValue     = new IntegerParameter("Maximum alpha value",     0x00,  0xFF,  0xFF);

    public PassHSBAEffect() {
        super("Pass", false, true);
        this.parameters.add(paramMinSThreshold);
        this.parameters.add(paramMinSValue);
        this.parameters.add(paramMaxSThreshold);
        this.parameters.add(paramMaxSValue);
        this.parameters.add(paramMinBThreshold);
        this.parameters.add(paramMinBValue);
        this.parameters.add(paramMaxBThreshold);
        this.parameters.add(paramMaxBValue);
        this.parameters.add(paramMinAThreshold);
        this.parameters.add(paramMinAValue);
        this.parameters.add(paramMaxAThreshold);
        this.parameters.add(paramMaxAValue);
    }

    @Override
    protected void filter(float in_h, float in_s, float in_b, int in_a) {
        final double minSThreshold = paramMinSThreshold.getValue();
        final double minSValue     = paramMinSValue.getValue();
        final double maxSThreshold = paramMaxSThreshold.getValue();
        final double maxSValue     = paramMaxSValue.getValue();
        final double minBThreshold = paramMinBThreshold.getValue();
        final double minBValue     = paramMinBValue.getValue();
        final double maxBThreshold = paramMaxBThreshold.getValue();
        final double maxBValue     = paramMaxBValue.getValue();
        final int    minAThreshold = paramMinAThreshold.getValue();
        final int    minAValue     = paramMinAValue.getValue();
        final int    maxAThreshold = paramMaxAThreshold.getValue();
        final int    maxAValue     = paramMaxAValue.getValue();
        out_h = in_h;
        out_s = in_s < minSThreshold ? (float) minSValue : (in_s >= maxSThreshold ? (float) maxSValue : in_s);
        out_b = in_b < minBThreshold ? (float) minBValue : (in_b >= maxBThreshold ? (float) maxBValue : in_b);
        out_a = in_a < minAThreshold ?         minAValue : (in_a >= maxAThreshold ?         maxAValue : in_a);
    }

}
