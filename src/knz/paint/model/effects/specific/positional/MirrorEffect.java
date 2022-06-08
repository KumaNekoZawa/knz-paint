package knz.paint.model.effects.specific.positional;

import knz.paint.model.effects.parameter.DoubleParameter;

public class MirrorEffect extends AbstractPositionalEffect {

    private DoubleParameter paramX = new DoubleParameter("X", 0, 0, 1);
    private DoubleParameter paramY = new DoubleParameter("Y", 0, 0, 1);

    public MirrorEffect() {
        super("Mirror");
        this.parameters.add(paramX);
        this.parameters.add(paramY);
    }

    @Override
    protected void filter(int width, int height, int toX, int toY) {
        final double x = paramX.getValue();
        final double y = paramY.getValue();
        final int xw = (int) (x * width);
        final int yh = (int) (y * height);
        fromX = (x < 0.5 ? xw < toX : toX < xw) ? toX : (2 * xw - toX);
        fromY = (y < 0.5 ? yh < toY : toY < yh) ? toY : (2 * yh - toY);
    }

}
