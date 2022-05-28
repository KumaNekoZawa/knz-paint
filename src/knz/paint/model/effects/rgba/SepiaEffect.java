package knz.paint.model.effects.rgba;

public class SepiaEffect extends RGBAEffect {

    public SepiaEffect() {
        super("Sepia");
    }

    @Override
    public void filter() {
        final int in_r = r;
        final int in_g = g;
        final int in_b = b;
        r = (int) (0.393 * in_r + 0.769 * in_g + 0.189 * in_b);
        g = (int) (0.349 * in_r + 0.686 * in_g + 0.168 * in_b);
        b = (int) (0.272 * in_r + 0.534 * in_g + 0.131 * in_b);
    }

}
