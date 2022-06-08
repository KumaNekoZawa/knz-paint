package knz.paint.model.effects.specific.positional.polar;

import knz.paint.model.effects.parameter.DoubleParameter;

// XXX this is just a special case of the AdjustPolarEffect
public class RotationEffect extends AbstractPolarEffect {

    private DoubleParameter paramAngle = new DoubleParameter("Angle", -180, 0, 180);

    public RotationEffect() {
        super("Rotation", true);
        this.parameters.add(paramAngle);
    }

    @Override
    protected void filter(double toR, double toA) {
        final double angle = Math.PI * paramAngle.getValue() / 180;
        fromA = toA + angle;
    }

}
