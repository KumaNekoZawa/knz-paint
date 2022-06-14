package knz.paint.model.effects.specific.xy;

import java.awt.Color;

import knz.paint.model.effects.parameter.ColorParameter;
import knz.paint.model.effects.parameter.DoubleParameter;

public class SaltPepperEffect extends AbstractXYEffect {

    private DoubleParameter paramAmount      = new DoubleParameter("Amount", 0, 0, 1);
    private DoubleParameter paramRatio       = new DoubleParameter("Ratio", 0, 0.5, 1);
    private ColorParameter  paramColorSalt   = new ColorParameter("Salt color",   Color.WHITE);
    private ColorParameter  paramColorPepper = new ColorParameter("Pepper color", Color.BLACK);

    public SaltPepperEffect() {
        super("Salt & pepper");
        this.parameters.add(paramAmount);
        this.parameters.add(paramRatio);
        this.parameters.add(paramColorSalt);
        this.parameters.add(paramColorPepper);
    }

    @Override
    protected void filter(int width, int height, int x, int y) {
        final double amount      = paramAmount.getValue();
        final double ratio       = paramRatio.getValue();
        final Color  colorSalt   = paramColorSalt.getValue();
        final Color  colorPepper = paramColorPepper.getValue();
        if (Math.random() < amount) {
            out_affected = true;
            out_color = Math.random() < ratio
                ? colorSalt
                : colorPepper;
        }
    }

}
