package knz.paint.model.effects.specific.hsba;

public class NegateHSBAEffect extends AbstractHSBAEffect {

    public NegateHSBAEffect() {
        super("Negate", true, true);
    }

    @Override
    protected void filter(float in_h, float in_s, float in_b, int in_a) {
        out_h =    1 - in_h;
        out_s =    1 - in_s;
        out_b =    1 - in_b;
        out_a = 0xFF - in_a;
    }

}
