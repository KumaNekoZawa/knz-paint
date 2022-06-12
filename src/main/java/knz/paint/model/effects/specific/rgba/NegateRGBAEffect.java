package knz.paint.model.effects.specific.rgba;

/* also called "Invert colors" or "Bit not" */
public class NegateRGBAEffect extends AbstractRGBAEffect {

    public NegateRGBAEffect() {
        super("Negate RGBA", true, true);
    }

    @Override
    protected void filter(int in_r, int in_g, int in_b, int in_a) {
        out_r = 0xFF - in_r;
        out_g = 0xFF - in_g;
        out_b = 0xFF - in_b;
        out_a = 0xFF - in_a;
    }

}
