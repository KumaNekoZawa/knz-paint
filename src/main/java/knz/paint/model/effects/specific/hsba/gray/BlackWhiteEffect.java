package knz.paint.model.effects.specific.hsba.gray;

import knz.paint.model.effects.parameter.BooleanParameter;
import knz.paint.model.effects.parameter.DoubleParameter;

public class BlackWhiteEffect extends AbstractGrayEffect {

    // FIXME these two parameters don't work well together

    private BooleanParameter paramRandom    = new BooleanParameter("Random", false);
    private DoubleParameter  paramThreshold = new DoubleParameter("Threshold", 0, 0.5, 1);

    public BlackWhiteEffect() {
        super("Black & white");
        this.parameters.add(paramRandom);
        this.parameters.add(paramThreshold);
    }

    @Override
    protected void filter(float in_b, int in_a) {
        final boolean random    = paramRandom.getValue();
        final double  threshold = random ? Math.random() : paramThreshold.getValue();
        out_b = in_b < threshold ? 0 : 1;
        out_a = in_a;
    }

}
