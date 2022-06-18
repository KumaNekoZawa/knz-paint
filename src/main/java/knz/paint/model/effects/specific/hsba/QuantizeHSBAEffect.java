package knz.paint.model.effects.specific.hsba;

import knz.paint.model.effects.parameter.IntegerParameter;

public class QuantizeHSBAEffect extends AbstractHSBAEffect {

    private static final int[] DIVISORS_OF_360 = new int[] { 1, 2, 3, 4, 5, 6, 8, 9, 10, 12, 15, 18, 20, 24, 30, 36, 40, 45, 60, 72, 90, 120, 180 };

    private IntegerParameter paramDivisorH = new IntegerParameter("Divisor hue",        0, 0, DIVISORS_OF_360.length - 1);
    private IntegerParameter paramDivisorS = new IntegerParameter("Divisor saturation", 0, 0, 7);
    private IntegerParameter paramDivisorB = new IntegerParameter("Divisor brightness", 0, 0, 7);
    private IntegerParameter paramDivisorA = new IntegerParameter("Divisor alpha",      0, 0, 7);

    public QuantizeHSBAEffect() {
        super("Quantize", false, true);
        this.parameters.add(paramDivisorH);
        this.parameters.add(paramDivisorS);
        this.parameters.add(paramDivisorB);
        this.parameters.add(paramDivisorA);
    }

    @Override
    protected void filter(float in_h, float in_s, float in_b, int in_a) {
        final int divisorH = DIVISORS_OF_360[paramDivisorH.getValue()];
        final int divisorS = 1 << paramDivisorS.getValue();
        final int divisorB = 1 << paramDivisorB.getValue();
        final int divisorA = 1 << paramDivisorA.getValue();
        final float multiplier = 0x100;
        out_h = (((int)       (360f * in_h)) / divisorH) * divisorH / 360f;
        out_s = (((int) (multiplier * in_s)) / divisorS) * divisorS / multiplier;
        out_b = (((int) (multiplier * in_b)) / divisorB) * divisorB / multiplier;
        out_a =                      (in_a   / divisorA) * divisorA;
    }

}
