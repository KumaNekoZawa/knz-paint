package knz.paint.model.effects.rgba;

import knz.paint.model.effects.DoubleParameter;

public class NoiseEffect extends RGBAEffect {

    private DoubleParameter amount = new DoubleParameter("Amount", 0, 0, 1);

    public NoiseEffect() {
        super("Noise");
        this.parameters.add(amount);
    }

    @Override
    public void filter() {
        final double a = amount.getValue();
        final double z = 1 - a;
        r = (int) (z * r + a * Math.random() * 0xFF);
        g = (int) (z * g + a * Math.random() * 0xFF);
        b = (int) (z * b + a * Math.random() * 0xFF);
    }

}
