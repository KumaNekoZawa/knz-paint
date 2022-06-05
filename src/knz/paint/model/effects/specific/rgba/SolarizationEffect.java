package knz.paint.model.effects.specific.rgba;

import knz.paint.model.effects.DoubleParameter;

public class SolarizationEffect extends AbstractRGBAEffect {

    private DoubleParameter paramThreshold = new DoubleParameter("Threshold", 0, 0.5, 1);

    public SolarizationEffect() {
        super("Solarization", true, true);
        this.parameters.add(paramThreshold);
    }

    @Override
    protected void filter(int x, int y, int in_r, int in_g, int in_b, int in_a) {
        final int threshold = (int) (paramThreshold.getValue() * 0x100);
        out_r = in_r < threshold ? 0xFF - in_r : in_r;
        out_g = in_g < threshold ? 0xFF - in_g : in_g;
        out_b = in_b < threshold ? 0xFF - in_b : in_b;
        out_a = in_a < threshold ? 0xFF - in_a : in_a;
    }

}
