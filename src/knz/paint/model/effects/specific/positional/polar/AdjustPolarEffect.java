package knz.paint.model.effects.specific.positional.polar;

import knz.paint.model.effects.DoubleParameter;

public class AdjustPolarEffect extends AbstractPolarEffect {

    private DoubleParameter paramFactorR   = new DoubleParameter("Factor r", 0, 1, 20);
    private DoubleParameter paramExponentR = new DoubleParameter("Exponent r", 0, 1, 2);
    private DoubleParameter paramFactorA   = new DoubleParameter("Factor a", 0, 1, 2);
    private DoubleParameter paramShiftA    = new DoubleParameter("Shift a", -180, 0, 180);

    public AdjustPolarEffect() {
        super("Adjust polar", true);
        this.parameters.add(paramFactorR);
        this.parameters.add(paramExponentR);
        this.parameters.add(paramFactorA);
        this.parameters.add(paramShiftA);
    }

    @Override
    protected void filter(int width, int height, int toX, int toY, double toR, double toA) {
        final double factorR   = paramFactorR.getValue();
        final double exponentR = paramExponentR.getValue();
        final double factorA   = paramFactorA.getValue();
        final double shiftA    = Math.PI * paramShiftA.getValue() / 180;
        fromR = factorR * Math.pow(toR, exponentR);
        fromA = factorA * toA + shiftA;
    }

}
