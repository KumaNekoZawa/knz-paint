package knz.paint.model.effects.specific.rgba;

import knz.paint.model.effects.parameter.DoubleParameter;

public class AdjustContrastEffect extends AbstractRGBAEffect {

    private DoubleParameter paramFactor = new DoubleParameter("Factor", 0, 1, MAX_FACTOR);

    public AdjustContrastEffect() {
        super("Adjust contrast", true, true);
        this.parameters.add(paramFactor);
    }

    @Override
    protected void filter(int in_r, int in_g, int in_b, int in_a) {
        final double factor = paramFactor.getValue();
        out_r = (int) (factor * (in_r - 0x80)) + 0x80;
        out_g = (int) (factor * (in_g - 0x80)) + 0x80;
        out_b = (int) (factor * (in_b - 0x80)) + 0x80;
        out_a = (int) (factor * (in_a - 0x80)) + 0x80;
    }

}
