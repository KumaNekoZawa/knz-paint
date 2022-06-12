package knz.paint.model.effects.specific.positional.polar;

import java.awt.Point;

import knz.paint.model.effects.parameter.BorderFillStrategy;
import knz.paint.model.effects.parameter.PointParameter;
import knz.paint.model.effects.specific.positional.AbstractPositionalEffect;

public abstract class AbstractPolarEffect extends AbstractPositionalEffect {

    private PointParameter paramHotspot = new PointParameter("Hotspot", 0.5, 0.5);

    protected double fromR, fromA;

    public AbstractPolarEffect(String name, BorderFillStrategy borderFillStrategy) {
        super("Polar." + name, borderFillStrategy);
        this.parameters.add(paramHotspot);
    }

    @Override
    protected void filter(int width, int height, int toX, int toY) {
        final Point hotspot = paramHotspot.getValue();
        final int size = Math.min(width, height);
        final double x = (toX - hotspot.x) / (double) size;
        final double y = (toY - hotspot.y) / (double) size;
        final double toR = Math.sqrt(x * x + y * y);
        final double toA = Math.atan2(x, y);
        fromR = toR;
        fromA = toA;
        filter(toR, toA);
        fromX = (int) (fromR * Math.sin(fromA) * size) + hotspot.x;
        fromY = (int) (fromR * Math.cos(fromA) * size) + hotspot.y;
    }

    protected abstract void filter(double toR, double toA);

}
