package knz.paint.model.effects.specific.rgba;

import knz.paint.model.effects.parameter.BooleanParameter;
import knz.paint.model.effects.parameter.IntegerParameter;

public class SolarizeRGBAEffect extends AbstractRGBAEffect {

    private BooleanParameter paramNegate    = new BooleanParameter("Negate", false);
    private IntegerParameter paramThreshold = new IntegerParameter("Threshold", 0x00, 0x00, 0x100);

    public SolarizeRGBAEffect() {
        super("Solarize", true, true);
        this.parameters.add(paramNegate);
        this.parameters.add(paramThreshold);
    }

    @Override
    protected void filter(int in_r, int in_g, int in_b, int in_a) {
        final int negate    = paramNegate.getValue() ? -1 : 1;
        final int threshold = paramThreshold.getValue();
        out_r = negate * Math.abs(in_r - threshold) + threshold;
        out_g = negate * Math.abs(in_g - threshold) + threshold;
        out_b = negate * Math.abs(in_b - threshold) + threshold;
        out_a = negate * Math.abs(in_a - threshold) + threshold;
    }

}
