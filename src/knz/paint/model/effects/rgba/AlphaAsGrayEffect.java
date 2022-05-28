package knz.paint.model.effects.rgba;

public class AlphaAsGrayEffect extends RGBAEffect {

    public AlphaAsGrayEffect() {
        super("Alpha as gray", false, true);
    }

    @Override
    public void filter(int x, int y, int in_r, int in_g, int in_b, int in_a) {
        out_r = in_a;
        out_g = in_a;
        out_b = in_a;
        out_a = 0xFF;
    }

}
