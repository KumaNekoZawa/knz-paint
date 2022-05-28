package knz.paint.model.effects.hsba;

import knz.paint.model.effects.DoubleParameter;

public class AdjustSaturationEffect extends HSBAEffect {

    private DoubleParameter exponent = new DoubleParameter("Exponent", 0, 1, 2);

    public AdjustSaturationEffect() {
        super("Adjust saturation");
        this.parameters.add(exponent);
    }

    @Override
    public void filter() {
        s = (float) Math.pow(s, 1 / exponent.getValue());
    }

}
