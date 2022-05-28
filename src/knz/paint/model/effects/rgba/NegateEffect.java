package knz.paint.model.effects.rgba;

/* also called "Invert colors" or "Bit not" */
public class NegateEffect extends RGBAEffect {

    public NegateEffect() {
        super("Negate", true, true);
    }

    @Override
    public void filter(int x, int y, int in_r, int in_g, int in_b, int in_a) {
        out_r = 0xFF - in_r;
        out_g = 0xFF - in_g;
        out_b = 0xFF - in_b;
        out_a = 0xFF - in_a;
    }

}
