package knz.paint.model.effects.specific.positional.polar;

import knz.paint.model.effects.parameter.BooleanParameter;

public class PolarCoordinatesEffect extends AbstractPolarEffect {

    private BooleanParameter paramSwap = new BooleanParameter("Swap x/y", false);

    public PolarCoordinatesEffect() {
        super("Polar coordinates", true);
        this.parameters.add(paramSwap);
    }

    @Override
    protected void filter(int width, int height, int toX, int toY, double toR, double toA) {
        final boolean swap = paramSwap.getValue();
        final double x =           toX      / (double) width;
        final double y = (height - toY - 1) / (double) height;
        fromR =                swap ? x : y;
        fromA = 2 * Math.PI * (swap ? y : x) - Math.PI;
    }

}
