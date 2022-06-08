package knz.paint.model.effects.specific.rgba;

import knz.paint.model.effects.parameter.IntegerParameter;

public class NormalizationEffect extends AbstractRGBAEffect {

    private IntegerParameter paramMin = new IntegerParameter("Min", 0x00, 0x00, 0xFF);
    private IntegerParameter paramMax = new IntegerParameter("Max", 0x00, 0xFF, 0xFF);

    public NormalizationEffect() {
        super("Normalization", true, true);
        this.parameters.add(paramMin);
        this.parameters.add(paramMax);
    }

    @Override
    protected void filter(int in_r, int in_g, int in_b, int in_a) {
        final int min = paramMin.getValue();
        final int max = paramMax.getValue();
        final int divider = Math.max(1, max - min);
        out_r = 0xFF * (in_r - min) / divider;
        out_g = 0xFF * (in_g - min) / divider;
        out_b = 0xFF * (in_b - min) / divider;
        out_a = 0xFF * (in_a - min) / divider;
    }

}
