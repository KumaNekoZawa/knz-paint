package knz.paint.model.effects.specific.positional.polar;

import knz.paint.model.effects.DoubleParameter;

public class SwirlEffect extends AbstractPolarEffect {

    private DoubleParameter paramFactor = new DoubleParameter("Factor", -2.5, 0, 2.5);

    public SwirlEffect() {
        super("Swirl", true);
        this.parameters.add(paramFactor);
    }

    @Override
    protected void filter(int width, int height, int toX, int toY, double toR, double toA) {
        final double factor = paramFactor.getValue();
        fromA = toA + (factor / 100) * toR;
    }

}
