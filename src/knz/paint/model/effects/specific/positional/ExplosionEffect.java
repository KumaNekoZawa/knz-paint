package knz.paint.model.effects.specific.positional;

import knz.paint.model.effects.parameter.BorderFillStrategy;
import knz.paint.model.effects.parameter.DoubleParameter;

public class ExplosionEffect extends AbstractPositionalEffect {

    private DoubleParameter paramRadius = new DoubleParameter("Radius", 0, 0, 100);

    public ExplosionEffect() {
        super("Explosion", BorderFillStrategy.EXTEND_EDGES);
        this.parameters.add(paramRadius);
    }

    @Override
    protected void filter(int width, int height, int toX, int toY) {
        final double radius = paramRadius.getValue();
        final double r = radius * Math.random();
        final double a = 2 * Math.PI * Math.random();
        fromX = (int) (r * Math.sin(a)) + toX;
        fromY = (int) (r * Math.cos(a)) + toY;
    }

}
