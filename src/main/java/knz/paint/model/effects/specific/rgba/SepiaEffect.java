package knz.paint.model.effects.specific.rgba;

public class SepiaEffect extends AbstractRGBAEffect {

    // FIXME add presets

    public SepiaEffect() {
        super("Sepia", false, false);
    }

    @Override
    protected void filter(int in_r, int in_g, int in_b, int in_a) {
        out_r = (int) (0.393 * in_r + 0.769 * in_g + 0.189 * in_b);
        out_g = (int) (0.349 * in_r + 0.686 * in_g + 0.168 * in_b);
        out_b = (int) (0.272 * in_r + 0.534 * in_g + 0.131 * in_b);
    }

}
