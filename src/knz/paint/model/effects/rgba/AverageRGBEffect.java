package knz.paint.model.effects.rgba;

public class AverageRGBEffect extends RGBAEffect {

    public AverageRGBEffect() {
        super("Average RGB", false, false);
    }

    @Override
    public void filter(int x, int y, int in_r, int in_g, int in_b, int in_a) {
        final int gray = (in_r + in_g + in_b) / 3;
        out_r = gray;
        out_g = gray;
        out_b = gray;
    }

}
