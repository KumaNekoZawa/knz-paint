package knz.paint.model.effects.hsba;

import knz.paint.model.effects.DoubleParameter;

public class AdjustBrightnessEffect extends HSBAEffect {

    private DoubleParameter exponent = new DoubleParameter("Exponent", 0, 1, 2);

    public AdjustBrightnessEffect() {
        super("Adjust brightness");
        this.parameters.add(exponent);
    }

    @Override
    public void filter() {
        b = (float) Math.pow(b, 1 / exponent.getValue());
    }

}
