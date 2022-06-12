package knz.paint.model.effects.specific.hsba;

import knz.paint.model.effects.parameter.DoubleParameter;

public class NoiseHSBAEffect extends AbstractHSBAEffect {

    private DoubleParameter paramAmountH = new DoubleParameter("Amount hue",        0, 0, 1);
    private DoubleParameter paramAmountS = new DoubleParameter("Amount saturation", 0, 0, 1);
    private DoubleParameter paramAmountB = new DoubleParameter("Amount brightness", 0, 0, 1);
    private DoubleParameter paramAmountA = new DoubleParameter("Amount alpha",      0, 0, 1);

    public NoiseHSBAEffect() {
        super("Noise");
        this.parameters.add(paramAmountH);
        this.parameters.add(paramAmountS);
        this.parameters.add(paramAmountB);
        this.parameters.add(paramAmountA);
    }

    @Override
    protected void filter(float in_h, float in_s, float in_b, int in_a) {
        final double amountH = paramAmountH.getValue();
        final double amountS = paramAmountS.getValue();
        final double amountB = paramAmountB.getValue();
        final double amountA = paramAmountA.getValue();
        final double negAmountH = 1 - amountH;
        final double negAmountS = 1 - amountS;
        final double negAmountB = 1 - amountB;
        final double negAmountA = 1 - amountA;
        out_h = (float) (negAmountH * in_h + amountH * Math.random());
        out_s = (float) (negAmountS * in_s + amountS * Math.random());
        out_b = (float) (negAmountB * in_b + amountB * Math.random());
        out_a =   (int) (negAmountA * in_a + amountA * Math.random() * 0xFF);
    }

}
