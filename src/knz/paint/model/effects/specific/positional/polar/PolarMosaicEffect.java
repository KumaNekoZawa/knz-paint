package knz.paint.model.effects.specific.positional.polar;

import knz.paint.model.effects.parameter.DoubleParameter;

public class PolarMosaicEffect extends AbstractPolarEffect {

    private DoubleParameter paramSizeR = new DoubleParameter("Size r", 0.01, 0.01, 1);
    private DoubleParameter paramSizeA = new DoubleParameter("Size a", 0.01, 0.01, 1);

    public PolarMosaicEffect() {
        super("Polar mosaic", true);
        this.parameters.add(paramSizeR);
        this.parameters.add(paramSizeA);
    }

    @Override
    protected void filter(int width, int height, int toX, int toY, double toR, double toA) {
        final double sizeR = 10 / paramSizeR.getValue();
        final double sizeA = 10 / paramSizeA.getValue();
        fromR =           ((int) (sizeR * toR          )) / sizeR;
        fromA = Math.PI * ((int) (sizeA * toA / Math.PI)) / sizeA;
    }

}
