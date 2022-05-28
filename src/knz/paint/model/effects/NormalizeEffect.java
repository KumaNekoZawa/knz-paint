package knz.paint.model.effects;

public class NormalizeEffect extends RGBAEffect {

    private EffectParameter min = new EffectParameter("Min", 0d, 0d, 1d);
    private EffectParameter max = new EffectParameter("Max", 0d, 1d, 1d);

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
