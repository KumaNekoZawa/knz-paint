package knz.paint.model.effects.specific.positional.polar;

import knz.paint.model.effects.parameter.BorderFillStrategy;
import knz.paint.model.effects.parameter.DoubleParameter;

public class AdjustPolarEffect extends AbstractPolarEffect {

    private DoubleParameter paramFactorR   = new DoubleParameter("Factor r",   0, 1, MAX_FACTOR);
    private DoubleParameter paramExponentR = new DoubleParameter("Exponent r", 0, 1, MAX_EXPONENT);
    private DoubleParameter paramFactorA   = new DoubleParameter("Factor a",   0, 1, MAX_FACTOR);
    private DoubleParameter paramShiftA    = new DoubleParameter("Shift a", "°", MIN_ANGLE, 0, MAX_ANGLE);

    public AdjustPolarEffect() {
        super("Adjust polar", BorderFillStrategy.FILL_TRANSPARENT, true);
        this.parameters.add(paramFactorR);
        this.parameters.add(paramExponentR);
        this.parameters.add(paramFactorA);
        this.parameters.add(paramShiftA);
    }

    @Override
    protected void filter(double toR, double toA) {
        final double factorR   = paramFactorR.getValue();
        final double exponentR = paramExponentR.getValue();
        final double factorA   = paramFactorA.getValue();
        final double shiftA    = paramShiftA.getValue() * Math.PI / 180;
        fromR = factorR * Math.pow(toR, exponentR);
        fromA = factorA * toA + shiftA;
    }

}
