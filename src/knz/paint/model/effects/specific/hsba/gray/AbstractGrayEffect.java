package knz.paint.model.effects.specific.hsba.gray;

import knz.paint.model.effects.specific.hsba.AbstractHSBAEffect;

public abstract class AbstractGrayEffect extends AbstractHSBAEffect {

    public AbstractGrayEffect(String name) {
        super(name);
    }

    @Override
    public void filter(int x, int y, float in_h, float in_s, float in_b, int in_a) {
        out_h = 0;
        out_s = 0;
        filter(x, y, in_b, in_a);
    }

    public abstract void filter(int x, int y, float in_b, int in_a);

}
