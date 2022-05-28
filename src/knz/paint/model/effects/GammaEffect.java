package knz.paint.model.effects;

public class GammaEffect extends RGBAEffect {

    private EffectParameter exponent = new EffectParameter("Exponent", 0d, 1d, 2d);

    public GammaEffect() {
        super("Gamma");
        this.parameters.add(exponent);
    }

    @Override
    public void filter() {
        final double e = exponent.getValue();
        r = (int) (255. * Math.pow(r / 255., e));
        g = (int) (255. * Math.pow(g / 255., e));
        b = (int) (255. * Math.pow(b / 255., e));
    }

}
