package knz.paint.model.effects.specific.positional;

import knz.paint.model.effects.IntegerParameter;

public class ExplosionEffect extends AbstractPositionalEffect {

    private IntegerParameter paramRadius = new IntegerParameter("Radius", 1, 1, 100);

    public ExplosionEffect() {
        super("Explosion");
        this.parameters.add(paramRadius);
    }

    @Override
    public void filter(int width, int height, int toX, int toY) {
        final double radius = paramRadius.getValue();
        final double r = radius * Math.random();
        final double a = 2 * Math.PI * Math.random();
        fromX = toX + (int) (r * Math.sin(a));
        fromY = toY + (int) (r * Math.cos(a));
    }

}
