package knz.paint.model.effects.specific.rgba;

public class SwapRedBlueEffect extends AbstractRGBAEffect {

    public SwapRedBlueEffect() {
        super("Swap red & blue", false, false);
    }

    @Override
    protected void filter(int in_r, int in_g, int in_b, int in_a) {
        out_r = in_b;
        out_g = in_g;
        out_b = in_r;
        out_a = in_a;
    }

}
