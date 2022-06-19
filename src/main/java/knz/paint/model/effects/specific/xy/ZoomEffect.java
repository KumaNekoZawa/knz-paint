package knz.paint.model.effects.specific.xy;

import knz.paint.model.effects.parameter.BorderFillStrategy;
import knz.paint.model.effects.parameter.DoubleParameter;

// XXX this is a special case of the AdjustCartesianEffect (the two factors being linked and inverted)
public class ZoomEffect extends AbstractXYEffect {

    private DoubleParameter paramFactor = new DoubleParameter("Factor", 0, 1, 10);

    public ZoomEffect() {
        super("Zoom", BorderFillStrategy.FILL_TRANSPARENT);
        this.parameters.add(paramFactor);
    }

    @Override
    protected void filter(int width, int height, int toX, int toY) {
        final double factor = 1 / paramFactor.getValue();
        fromX = (int) (factor * toX);
        fromY = (int) (factor * toY);
    }

}
