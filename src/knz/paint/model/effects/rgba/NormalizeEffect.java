package knz.paint.model.effects.rgba;

import knz.paint.model.effects.DoubleParameter;

public class NormalizeEffect extends RGBAEffect {

    private DoubleParameter min = new DoubleParameter("Min", 0, 0, 1);
    private DoubleParameter max = new DoubleParameter("Max", 0, 1, 1);

    public NormalizeEffect() {
        super("Normalize");
        this.parameters.add(min);
        this.parameters.add(max);
    }

    @Override
    public void filter() {
        final double minValue = min.getValue();
        final double maxValue = max.getValue();
        r = (int) (255. * (r / 255. - minValue) / (maxValue - minValue));
        g = (int) (255. * (g / 255. - minValue) / (maxValue - minValue));
        b = (int) (255. * (b / 255. - minValue) / (maxValue - minValue));
    }

}
