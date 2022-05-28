package knz.paint.model.effects.rgba;

import knz.paint.model.effects.DoubleParameter;

public class AdjustGammaEffect extends RGBAEffect {

    private DoubleParameter exponent = new DoubleParameter("Exponent", 0, 1, 2);

    public AdjustGammaEffect() {
        super("Adjust gamma");
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
