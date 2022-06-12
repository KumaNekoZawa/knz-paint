package knz.paint.model.effects.specific.rgba;

import knz.paint.model.effects.parameter.IntegerParameter;

public class NormalizeRGBAEffect extends AbstractRGBAEffect {

    private IntegerParameter paramMin = new IntegerParameter("Minimum", 0x00, 0x00, 0xFF);
    private IntegerParameter paramMax = new IntegerParameter("Maximum", 0x00, 0xFF, 0xFF);

    public NormalizeRGBAEffect() {
        super("Normalize", true, true);
        this.parameters.add(paramMin);
        this.parameters.add(paramMax);
    }

    @Override
    protected void filter(int in_r, int in_g, int in_b, int in_a) {
        final int min = paramMin.getValue();
        final int max = paramMax.getValue();
        final int divider = Math.max(1, max - min);
        out_r = (in_r - min) * 0xFF / divider;
        out_g = (in_g - min) * 0xFF / divider;
        out_b = (in_b - min) * 0xFF / divider;
        out_a = (in_a - min) * 0xFF / divider;
    }

}
