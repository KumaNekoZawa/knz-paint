package knz.paint.model.effects.specific.rgba;

import knz.paint.model.effects.parameter.BooleanParameter;
import knz.paint.model.effects.parameter.IntegerParameter;

public class SolarizationEffect extends AbstractRGBAEffect {

    private BooleanParameter paramNegate    = new BooleanParameter("Negate", false);
    private IntegerParameter paramThreshold = new IntegerParameter("Threshold", 0x00, 0x00, 0x100);

    public SolarizationEffect() {
        super("Solarization", true, true);
        this.parameters.add(paramNegate);
        this.parameters.add(paramThreshold);
    }

    @Override
    protected void filter(int in_r, int in_g, int in_b, int in_a) {
        final int negate    = paramNegate.getValue() ? -1 : 1;
        final int threshold = paramThreshold.getValue();
        out_r = threshold + negate * Math.abs(in_r - threshold);
        out_g = threshold + negate * Math.abs(in_g - threshold);
        out_b = threshold + negate * Math.abs(in_b - threshold);
        out_a = threshold + negate * Math.abs(in_a - threshold);
    }

}
