package knz.paint.model.effects.rgba;

public class SolarizationEffect extends RGBAEffect {

    // FIXME make threshold a parameter

    public SolarizationEffect() {
        super("Solarization", true, true);
    }

    @Override
    public void filter(int x, int y, int in_r, int in_g, int in_b, int in_a) {
        out_r = in_r < 0x80 ? 0xFF - in_r : in_r;
        out_g = in_g < 0x80 ? 0xFF - in_g : in_g;
        out_b = in_b < 0x80 ? 0xFF - in_b : in_b;
        out_a = in_a < 0x80 ? 0xFF - in_a : in_a;
    }

}
