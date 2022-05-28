package knz.paint.model.effects.rgba;

import knz.paint.model.effects.DoubleParameter;

// FIXME could also be implemented as an HSBA effect
public class SaltPepperEffect extends RGBAEffect {

    private DoubleParameter amountSalt   = new DoubleParameter("Salt",   0, 0, 1);
    private DoubleParameter amountPepper = new DoubleParameter("Pepper", 0, 0, 1);

    public SaltPepperEffect() {
        super("Salt & Pepper");
        this.parameters.add(amountSalt);
        this.parameters.add(amountPepper);
    }

    @Override
    public void filter() {
        if (Math.random() < amountSalt.getValue()) {
            r = 255;
            g = 255;
            b = 255;
            a = 255;
        } else if (Math.random() < amountPepper.getValue()) {
            r = 0;
            g = 0;
            b = 0;
            a = 255;
        }
    }

}
