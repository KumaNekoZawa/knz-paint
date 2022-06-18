package knz.paint.model.effects.specific.rgba;

import knz.paint.model.effects.parameter.IntegerParameter;

public class QuantizeRGBAEffect extends AbstractRGBAEffect {

    private IntegerParameter paramDivisor = new IntegerParameter("Divisor", 0, 0, 7);

    public QuantizeRGBAEffect() {
        super("Quantize", true, true);
        this.parameters.add(paramDivisor);
    }

    @Override
    protected void filter(int in_r, int in_g, int in_b, int in_a) {
        final int divisor = 1 << paramDivisor.getValue();
        out_r = (in_r / divisor) * divisor;
        out_g = (in_g / divisor) * divisor;
        out_b = (in_b / divisor) * divisor;
        out_a = (in_a / divisor) * divisor;
    }

}
