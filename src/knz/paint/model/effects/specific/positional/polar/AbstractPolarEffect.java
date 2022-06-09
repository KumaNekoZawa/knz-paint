package knz.paint.model.effects.specific.positional.polar;

import knz.paint.model.effects.parameter.BorderFillStrategy;
import knz.paint.model.effects.parameter.DoubleParameter;
import knz.paint.model.effects.specific.positional.AbstractPositionalEffect;

public abstract class AbstractPolarEffect extends AbstractPositionalEffect {

    private DoubleParameter paramShiftX = new DoubleParameter("Shift x", 0, 0.5, 1);
    private DoubleParameter paramShiftY = new DoubleParameter("Shift y", 0, 0.5, 1);

    protected double fromR, fromA;

    public AbstractPolarEffect(String name, BorderFillStrategy borderFillStrategy, boolean showDefaultParameters) {
        super(name, borderFillStrategy);
        if (showDefaultParameters) {
            this.parameters.add(paramShiftX);
            this.parameters.add(paramShiftY);
        }
    }

    @Override
    protected void filter(int width, int height, int toX, int toY) {
        final int shiftX = (int) (paramShiftX.getValue() * width);
        final int shiftY = (int) (paramShiftY.getValue() * height);
        final int size = Math.min(width, height);
        final double x = (toX - shiftX) / (double) size;
        final double y = (toY - shiftY) / (double) size;
        final double toR = Math.sqrt(x * x + y * y);
        final double toA = Math.atan2(x, y);
        fromR = toR;
        fromA = toA;
        filter(toR, toA);
        fromX = (int) (fromR * Math.sin(fromA) * size) + shiftX;
        fromY = (int) (fromR * Math.cos(fromA) * size) + shiftY;
    }

    protected abstract void filter(double toR, double toA);

}
