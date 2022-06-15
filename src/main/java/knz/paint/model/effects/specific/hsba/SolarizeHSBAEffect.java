package knz.paint.model.effects.specific.hsba;

import knz.paint.model.effects.parameter.BooleanParameter;
import knz.paint.model.effects.parameter.DoubleParameter;
import knz.paint.model.effects.parameter.IntegerParameter;

public class SolarizeHSBAEffect extends AbstractHSBAEffect {

    private BooleanParameter paramNegateS    = new BooleanParameter("Negate saturation", false);
    private DoubleParameter  paramThresholdS = new DoubleParameter("Threshold saturation", 0, 0, 1);
    private BooleanParameter paramNegateB    = new BooleanParameter("Negate brightness", false);
    private DoubleParameter  paramThresholdB = new DoubleParameter("Threshold brightness", 0, 0, 1);
    private BooleanParameter paramNegateA    = new BooleanParameter("Negate alpha", false);
    private IntegerParameter paramThresholdA = new IntegerParameter("Threshold alpha", 0x00, 0x00, 0x100);

    public SolarizeHSBAEffect() {
        super("Solarize", false, true);
        this.parameters.add(paramNegateS);
        this.parameters.add(paramThresholdS);
        this.parameters.add(paramNegateB);
        this.parameters.add(paramThresholdB);
        this.parameters.add(paramNegateA);
        this.parameters.add(paramThresholdA);
    }

    @Override
    protected void filter(float in_h, float in_s, float in_b, int in_a) {
        final int    negateS    = paramNegateS.getValue() ? -1 : 1;
        final double thresholdS = paramThresholdS.getValue();
        final int    negateB    = paramNegateB.getValue() ? -1 : 1;
        final double thresholdB = paramThresholdB.getValue();
        final int    negateA    = paramNegateA.getValue() ? -1 : 1;
        final int    thresholdA = paramThresholdA.getValue();
        out_h = in_h;
        out_s = paramThresholdS.isSet() ? (float) (negateS * Math.abs(in_s - thresholdS) + thresholdS) : in_s;
        out_b = paramThresholdB.isSet() ? (float) (negateB * Math.abs(in_b - thresholdB) + thresholdB) : in_b;
        out_a =                                    negateA * Math.abs(in_a - thresholdA) + thresholdA;
    }

}
