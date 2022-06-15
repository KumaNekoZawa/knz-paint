package knz.paint.model.effects.specific.hsba.gray;

import knz.paint.model.effects.parameter.DoubleParameter;

public class ThresholdEffect extends AbstractGrayEffect {

    private DoubleParameter paramThreshold = new DoubleParameter("Threshold", 0, 0.5, 1);

    public ThresholdEffect() {
        super("Threshold", false);
        this.parameters.add(paramThreshold);
    }

    @Override
    protected void filter(float in_b, int in_a) {
        final double threshold = paramThreshold.getValue();
        out_b = in_b < threshold ? 0 : 1;
        out_a = in_a;
    }

}
