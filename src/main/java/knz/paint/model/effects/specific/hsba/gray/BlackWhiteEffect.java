package knz.paint.model.effects.specific.hsba.gray;

public class BlackWhiteEffect extends AbstractGrayEffect {

    public BlackWhiteEffect() {
        super("Black & white", false);
    }

    @Override
    protected void filter(float in_b, int in_a) {
        out_b = in_b < Math.random() ? 0 : 1;
        out_a = in_a;
    }

}
