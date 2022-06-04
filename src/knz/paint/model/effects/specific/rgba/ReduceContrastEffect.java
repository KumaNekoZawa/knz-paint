package knz.paint.model.effects.specific.rgba;

public class ReduceContrastEffect extends AbstractRGBAEffect {

    // FIXME make this a preset of SepiaEffect

    public ReduceContrastEffect() {
        super("Reduce contrast", false, false);
    }

    @Override
    public void filter(int x, int y, int in_r, int in_g, int in_b, int in_a) {
        out_r = (2 * in_r + in_g + in_b) / 4;
        out_g = (in_r + 2 * in_g + in_b) / 4;
        out_b = (in_r + in_g + 2 * in_b) / 4;
    }

}
