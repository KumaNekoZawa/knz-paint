package knz.paint.model.effects.specific.hsba;

import knz.paint.model.effects.parameter.BooleanParameter;
import knz.paint.model.effects.parameter.DoubleParameter;

public class SaltPepperEffect extends AbstractHSBAEffect {

    private BooleanParameter paramAlpha  = new BooleanParameter("Affect alpha", false);
    private DoubleParameter  paramAmount = new DoubleParameter("Amount", 0, 0, 1);
    private DoubleParameter  paramRatio  = new DoubleParameter("Ratio", 0, 0.5, 1);

    public SaltPepperEffect() {
        super("Salt & Pepper");
        this.parameters.add(paramAlpha);
        this.parameters.add(paramAmount);
        this.parameters.add(paramRatio);
    }

    @Override
    protected void filter(int x, int y, float in_h, float in_s, float in_b, int in_a) {
        final boolean alpha  = paramAlpha.getValue();
        final double  amount = paramAmount.getValue();
        final double  ratio  = paramRatio.getValue();
        if (Math.random() < amount) {
            out_h = 0;
            out_s = 0;
            out_b = Math.random() < ratio ? 1 : 0;
            if (alpha) {
                out_a = 0xFF;
            }
        }
    }

}
