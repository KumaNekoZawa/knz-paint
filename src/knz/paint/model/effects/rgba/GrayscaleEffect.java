package knz.paint.model.effects.rgba;

public class GrayscaleEffect extends RGBAEffect {

    public GrayscaleEffect() {
        super("Grayscale", false, false);
    }

    @Override
    public void filter(int x, int y, int in_r, int in_g, int in_b, int in_a) {
        // FIXME also have other factors as presets (see Wikipedia: Grayscale: ITU-R standards)
        final int gray = (int) (0.299 * in_r + 0.587 * in_g + 0.114 * in_b);
        out_r = gray;
        out_g = gray;
        out_b = gray;
    }

}
