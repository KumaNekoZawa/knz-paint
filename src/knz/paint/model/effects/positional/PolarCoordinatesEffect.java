package knz.paint.model.effects.positional;

import knz.paint.model.effects.BooleanParameter;

public class PolarCoordinatesEffect extends AbstractPositionalEffect {

    private BooleanParameter paramSwap = new BooleanParameter("Swap x/y", false);

    public PolarCoordinatesEffect() {
        super("Polar coordinates");
        this.parameters.add(paramSwap);
    }

    @Override
    public void filter(int width, int height, int toX, int toY) {
        final boolean swap = paramSwap.getValue();
        final double x = toX / (double) width;
        final double y = (height - toY - 1) / (double) height;
        final double r = swap ? x : y;
        final double a = 2 * Math.PI * (swap ? y : x);
        fromX = (int) ((r * Math.sin(a) + 1) * width  / 2);
        fromY = (int) ((r * Math.cos(a) + 1) * height / 2);
    }

}
