package knz.paint.model.effects.rgba;

import knz.paint.model.effects.DoubleParameter;

public class AdjustContrastEffect extends RGBAEffect {

    private DoubleParameter factor = new DoubleParameter("Factor", 0, 1, 2);

    public AdjustContrastEffect() {
        super("Adjust contrast");
        this.parameters.add(factor);
    }

    @Override
    public void filter() {
        final double f = factor.getValue();
        r = (int) (f * (r - 0x80)) + 0x80;
        g = (int) (f * (g - 0x80)) + 0x80;
        b = (int) (f * (b - 0x80)) + 0x80;
    }

}
