package knz.paint.model.effects.specific.positional.polar;

import knz.paint.model.effects.parameter.BorderFillStrategy;
import knz.paint.model.effects.parameter.DoubleParameter;

public class PolarMosaicEffect extends AbstractPolarEffect {

    private DoubleParameter paramSizeR = new DoubleParameter("Size r", 0.01, 0.01, 1);
    private DoubleParameter paramSizeA = new DoubleParameter("Size a", 0.01, 0.01, 1);

    public PolarMosaicEffect() {
        super("Polar mosaic", BorderFillStrategy.EXTEND_EDGES, true);
        this.parameters.add(paramSizeR);
        this.parameters.add(paramSizeA);
    }

    @Override
    protected void filter(double toR, double toA) {
        final double sizeR = 10 / paramSizeR.getValue();
        final double sizeA = 10 / paramSizeA.getValue();
        fromR = ((int) (sizeR * toR))                     / sizeR;
        fromA = ((int) (sizeA * toA / Math.PI)) * Math.PI / sizeA;
    }

}
