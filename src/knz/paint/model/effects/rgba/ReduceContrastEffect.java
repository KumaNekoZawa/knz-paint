package knz.paint.model.effects.rgba;

public class ReduceContrastEffect extends RGBAEffect {

    public ReduceContrastEffect() {
        super("Reduce contrast", false, false);
    }

    @Override
    public void filter(int x, int y, int in_r, int in_g, int in_b, int in_a) {
        out_r = (in_g + in_b) / 2;
        out_g = (in_r + in_b) / 2;
        out_b = (in_r + in_g) / 2;
    }

}
