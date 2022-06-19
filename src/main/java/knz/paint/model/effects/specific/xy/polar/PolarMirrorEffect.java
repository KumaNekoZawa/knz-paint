package knz.paint.model.effects.specific.xy.polar;

import knz.paint.model.effects.parameter.BorderFillStrategy;
import knz.paint.model.effects.parameter.DoubleParameter;

public class PolarMirrorEffect extends AbstractPolarEffect {

    private DoubleParameter paramAngle = new DoubleParameter("Angle", "°", MIN_ANGLE, 0, MAX_ANGLE);

    public PolarMirrorEffect() {
        super("Polar mirror", BorderFillStrategy.EXTEND_EDGES);
        this.parameters.add(paramAngle);
    }

    @Override
    protected void filter(double toR, double toA) {
        final double angle = paramAngle.getValue() * Math.PI / 180;
        fromR = toR;
        fromA = floorMod(toA - angle, 2 * Math.PI) < Math.PI ? 2 * angle - toA : toA;
    }

    private static double floorMod(double x, double y) {
        return x - Math.floor(x / y) * y;
    }

}
