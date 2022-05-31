package knz.paint.model.effects.rgba;

import knz.paint.model.effects.DoubleParameter;

public class NoiseEffect extends AbstractRGBAEffect {

    private DoubleParameter paramAmount = new DoubleParameter("Amount", 0, 0, 1);

    public NoiseEffect() {
        super("Noise", true, true);
        this.parameters.add(paramAmount);
    }

    @Override
    public void filter(int x, int y, int in_r, int in_g, int in_b, int in_a) {
        final double amount = paramAmount.getValue();
        final double negAmount = 1 - amount;
        out_r = (int) (negAmount * in_r + amount * Math.random() * 0xFF);
        out_g = (int) (negAmount * in_g + amount * Math.random() * 0xFF);
        out_b = (int) (negAmount * in_b + amount * Math.random() * 0xFF);
        out_a = (int) (negAmount * in_a + amount * Math.random() * 0xFF);
    }

}
