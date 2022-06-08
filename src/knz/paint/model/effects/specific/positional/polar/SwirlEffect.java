package knz.paint.model.effects.specific.positional.polar;

import knz.paint.model.effects.parameter.DoubleParameter;

public class SwirlEffect extends AbstractPolarEffect {

    private DoubleParameter paramFactor = new DoubleParameter("Factor", -10, 0, 10);

    public SwirlEffect() {
        super("Swirl", true);
        this.parameters.add(paramFactor);
    }

    @Override
    protected void filter(double toR, double toA) {
        final double factor = paramFactor.getValue();
        fromA = toA + factor * toR;
    }

}
