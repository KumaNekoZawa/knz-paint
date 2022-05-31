package knz.paint.model.effects.rgba;

public class ExtractRGBAEffect extends AbstractRGBAEffect {

    public ExtractRGBAEffect() {
        super("Extract RGBA", true, true);
    }

    @Override
    public void filter(int x, int y, int in_r, int in_g, int in_b, int in_a) {
        out_r = 0x00;
        out_g = 0x00;
        out_b = 0x00;
        out_a = 0xFF;
    }

}
