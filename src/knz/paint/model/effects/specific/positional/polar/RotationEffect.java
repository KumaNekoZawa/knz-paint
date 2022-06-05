package knz.paint.model.effects.specific.positional.polar;

import knz.paint.model.effects.DoubleParameter;

public class RotationEffect extends AbstractPolarEffect {

    private DoubleParameter paramAngle = new DoubleParameter("Angle", -180, 0, 180);

    public RotationEffect() {
        super("Rotation", true);
        this.parameters.add(paramAngle);
    }

    @Override
    protected void filter(int width, int height, int toX, int toY, double toR, double toA) {
        final double angle = Math.PI * paramAngle.getValue() / 180;
        fromA = toA + angle;
    }

}
