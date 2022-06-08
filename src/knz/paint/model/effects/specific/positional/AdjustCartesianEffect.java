package knz.paint.model.effects.specific.positional;

import knz.paint.model.effects.parameter.BorderFillStrategy;
import knz.paint.model.effects.parameter.DoubleParameter;

public class AdjustCartesianEffect extends AbstractPositionalEffect {

    private DoubleParameter paramFactorX = new DoubleParameter("Factor x", 0, 1, 10);
    private DoubleParameter paramShiftX  = new DoubleParameter("Shift x", -1, 0, 1);
    private DoubleParameter paramFactorY = new DoubleParameter("Factor y", 0, 1, 10);
    private DoubleParameter paramShiftY  = new DoubleParameter("Shift y", -1, 0, 1);

    public AdjustCartesianEffect() {
        super("Adjust cartesian", BorderFillStrategy.FILL_TRANSPARENT);
        this.parameters.add(paramFactorX);
        this.parameters.add(paramShiftX);
        this.parameters.add(paramFactorY);
        this.parameters.add(paramShiftY);
    }

    @Override
    protected void filter(int width, int height, int toX, int toY) {
        final double factorX = paramFactorX.getValue();
        final int    shiftX  = (int) (paramShiftX.getValue() * width);
        final double factorY = paramFactorY.getValue();
        final int    shiftY  = (int) (paramShiftY.getValue() * height);
        fromX = (int) (factorX * toX) + shiftX;
        fromY = (int) (factorY * toY) + shiftY;
    }

}
