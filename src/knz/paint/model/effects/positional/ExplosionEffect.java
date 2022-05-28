package knz.paint.model.effects.positional;

import knz.paint.model.effects.IntegerParameter;

public class ExplosionEffect extends PositionalEffect {

    private IntegerParameter radius = new IntegerParameter("Radius", 1, 1, 100);

    public ExplosionEffect() {
        super("Explosion");
        this.parameters.add(radius);
    }

    @Override
    public void filter(int width, int height, int toX, int toY) {
        final double r = radius.getValue() * Math.random();
        final double a = 2 * Math.PI * Math.random();
        fromX = toX + (int) (r * Math.sin(a));
        fromY = toY + (int) (r * Math.cos(a));
    }

}
