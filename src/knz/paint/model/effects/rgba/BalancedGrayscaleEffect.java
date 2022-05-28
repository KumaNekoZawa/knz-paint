package knz.paint.model.effects.rgba;

public class BalancedGrayscaleEffect extends RGBAEffect {

    public BalancedGrayscaleEffect() {
        super("Balanced grayscale");
    }

    @Override
    public void filter() {
        // FIXME also have other factors as presets (see Wikipedia: Grayscale: ITU-R standards)
        final int gray = (int) (0.299 * r + 0.587 * g + 0.114 * b);
        r = gray;
        g = gray;
        b = gray;
    }

}
