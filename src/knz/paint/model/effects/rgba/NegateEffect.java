package knz.paint.model.effects.rgba;

/* also called Invert */
public class NegateEffect extends RGBAEffect {

    public NegateEffect() {
        super("Negate");
    }

    @Override
    public void filter() {
        r = 0xFF - r;
        g = 0xFF - g;
        b = 0xFF - b;
    }

}
