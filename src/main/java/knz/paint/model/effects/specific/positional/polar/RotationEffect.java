package knz.paint.model.effects.specific.positional.polar;

import knz.paint.model.effects.parameter.BorderFillStrategy;
import knz.paint.model.effects.parameter.DoubleParameter;

// XXX this is a special case of the AdjustPolarEffect
public class RotationEffect extends AbstractPolarEffect {

    private DoubleParameter paramAngle = new DoubleParameter("Angle", "°", MIN_ANGLE, 0, MAX_ANGLE);

    public RotationEffect() {
        super("Rotation", BorderFillStrategy.FILL_TRANSPARENT);
        this.parameters.add(paramAngle);
    }

    @Override
    protected void filter(double toR, double toA) {
        final double angle = paramAngle.getValue() * Math.PI / 180;
        fromR = toR;
        fromA = toA + angle;
    }

}
