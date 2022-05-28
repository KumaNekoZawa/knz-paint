package knz.paint.model.effects.positional;

import knz.paint.model.effects.DoubleParameter;

public class RotationEffect extends PositionalEffect {

    private DoubleParameter paramAngle = new DoubleParameter("Angle", 0, 0, 360);

    public RotationEffect() {
        super("Rotation");
        this.parameters.add(paramAngle);
    }

    @Override
    public void filter(int width, int height, int toX, int toY) {
        final double angle = Math.PI * paramAngle.getValue() / 180;
        final int x = toX - width / 2;
        final int y = toY - height / 2;
        final double r = Math.sqrt(x * x + y * y);
        final double a = Math.atan2(x, y) + angle;
        fromX = (int) (r * Math.sin(a)) + width / 2;
        fromY = (int) (r * Math.cos(a)) + height / 2;
    }

}
