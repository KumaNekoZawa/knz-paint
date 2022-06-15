package knz.paint.model.effects.specific.positional.polar;

import knz.paint.model.effects.parameter.BorderFillStrategy;
import knz.paint.model.effects.parameter.DoubleParameter;
import knz.paint.model.effects.parameter.Preset;
import knz.paint.model.effects.parameter.PresetParameter;

public class AdjustPolarEffect extends AbstractPolarEffect {

    private PresetParameter paramPresets   = new PresetParameter(
        new String[] { "Border fill strategy", "Zoom", "Exponent r", "Factor a", "Rotate" },
        new Preset("Default",      BorderFillStrategy.FILL_TRANSPARENT, 1,    1,   1, 0),
        new Preset("Caricature",   BorderFillStrategy.FILL_TRANSPARENT, 0.70, 0.5, 1, 0),
        new Preset("Fisheye lens", BorderFillStrategy.FILL_TRANSPARENT, 1,    2,   1, 0)
    );
    private DoubleParameter paramFactorR   = new DoubleParameter("Zoom",       0, 1, MAX_FACTOR);
    private DoubleParameter paramExponentR = new DoubleParameter("Exponent r", 0, 1, MAX_EXPONENT);
    private DoubleParameter paramFactorA   = new DoubleParameter("Factor a",   0, 1, MAX_FACTOR);
    private DoubleParameter paramShiftA    = new DoubleParameter("Rotate", "°", MIN_ANGLE, 0, MAX_ANGLE);

    public AdjustPolarEffect() {
        super("Adjust polar", BorderFillStrategy.FILL_TRANSPARENT);
        this.parameters.add(paramPresets);
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
