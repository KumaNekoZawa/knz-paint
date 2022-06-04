package knz.paint.model.effects.specific.hsba.gray;

import knz.paint.model.effects.BooleanParameter;
import knz.paint.model.effects.DoubleParameter;

public class BlackWhiteEffect extends AbstractGrayEffect {

    private BooleanParameter paramRandom    = new BooleanParameter("Random", true);
    private DoubleParameter  paramThreshold = new DoubleParameter("Threshold", 0, 0.5, 1);

    public BlackWhiteEffect() {
        super("Black & White");
        this.parameters.add(paramRandom);
        this.parameters.add(paramThreshold);
    }

    @Override
    public void filter(int x, int y, float in_b, int in_a) {
        final boolean random    = paramRandom.getValue();
        final double  threshold = random ? Math.random() : paramThreshold.getValue();
        out_b = in_b < threshold ? 0 : 1;
    }

}
