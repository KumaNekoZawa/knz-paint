package knz.paint.model.effects.specific.hsba;

import knz.paint.model.effects.parameter.DoubleParameter;
import knz.paint.model.effects.parameter.IntegerParameter;

public class NormalizeHSBAEffect extends AbstractHSBAEffect {

    private DoubleParameter  paramMinS = new DoubleParameter("Minimum saturation", 0, 0, 1);
    private DoubleParameter  paramMaxS = new DoubleParameter("Maximum saturation", 0, 1, 1);
    private DoubleParameter  paramMinB = new DoubleParameter("Minimum brightness", 0, 0, 1);
    private DoubleParameter  paramMaxB = new DoubleParameter("Maximum brightness", 0, 1, 1);
    private IntegerParameter paramMinA = new IntegerParameter("Minimum alpha", 0x00, 0x00, 0xFF);
    private IntegerParameter paramMaxA = new IntegerParameter("Maximum alpha", 0x00, 0xFF, 0xFF);

    public NormalizeHSBAEffect() {
        super("Normalize", false, true);
        this.parameters.add(paramMinS);
        this.parameters.add(paramMaxS);
        this.parameters.add(paramMinB);
        this.parameters.add(paramMaxB);
        this.parameters.add(paramMinA);
        this.parameters.add(paramMaxA);
    }

    @Override
    protected void filter(float in_h, float in_s, float in_b, int in_a) {
        final double minS = paramMinS.getValue();
        final double maxS = paramMaxS.getValue();
        final double minB = paramMinB.getValue();
        final double maxB = paramMaxB.getValue();
        final int    minA = paramMinA.getValue();
        final int    maxA = paramMaxA.getValue();
        out_h = in_h;
        out_s = paramMinS.isSet() || paramMaxS.isSet() ? (float) ((in_s - minS)        / Math.max(0.01, maxS - minS)) : in_s;
        out_b = paramMinB.isSet() || paramMaxB.isSet() ? (float) ((in_b - minB)        / Math.max(0.01, maxB - minB)) : in_b;
        out_a =                                                   (in_a - minA) * 0xFF / Math.max(1,    maxA - minA);
    }

}
