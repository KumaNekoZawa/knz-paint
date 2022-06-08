package knz.paint.model.effects.specific.rgba;

import knz.paint.model.effects.parameter.BooleanParameter;
import knz.paint.model.effects.parameter.IntegerParameter;

public class SolarizationEffect extends AbstractRGBAEffect {

    private BooleanParameter paramNegate    = new BooleanParameter("Negate", false);
    private IntegerParameter paramThreshold = new IntegerParameter("Threshold", 0x00, 0x00, 0x100);

    public SolarizationEffect() {
        super("Solarization", true, true);
        this.parameters.add(paramNegate);
        this.parameters.add(paramThreshold);
    }

    @Override
    protected void filter(int in_r, int in_g, int in_b, int in_a) {
        final boolean negate    = paramNegate.getValue();
        final int     threshold = paramThreshold.getValue();
        out_r = (negate ? in_r > threshold : in_r < threshold) ? 0xFF - in_r : in_r;
        out_g = (negate ? in_g > threshold : in_g < threshold) ? 0xFF - in_g : in_g;
        out_b = (negate ? in_b > threshold : in_b < threshold) ? 0xFF - in_b : in_b;
        out_a = (negate ? in_a > threshold : in_a < threshold) ? 0xFF - in_a : in_a;
    }

}
