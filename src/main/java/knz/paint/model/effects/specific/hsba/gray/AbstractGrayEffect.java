package knz.paint.model.effects.specific.hsba.gray;

import knz.paint.model.effects.specific.hsba.AbstractHSBAEffect;

public abstract class AbstractGrayEffect extends AbstractHSBAEffect {

    public AbstractGrayEffect(String name, boolean affectsAlpha) {
        super("Gray." + name, false, affectsAlpha);
    }

    @Override
    protected void filter(float in_h, float in_s, float in_b, int in_a) {
        filter(in_b, in_a);
        out_h = 0;
        out_s = 0;
    }

    protected abstract void filter(float in_b, int in_a);

}
