package knz.paint.model.effects.specific;

import java.awt.Color;
import java.awt.image.BufferedImage;

import knz.paint.model.effects.BooleanParameter;
import knz.paint.model.effects.DoubleParameter;

public class PolarBentleyEffect extends AbstractEffect {

    private BooleanParameter paramSat     = new BooleanParameter("Saturation instead of Brightness", false);
    private DoubleParameter  paramFactorR = new DoubleParameter("Factor r", 0, 0, 250);
    private DoubleParameter  paramPhaseA  = new DoubleParameter("Phase a", -180, 0, 180);

    public PolarBentleyEffect() {
        super("Polar Bentley effect");
        this.parameters.add(paramSat);
        this.parameters.add(paramFactorR);
        this.parameters.add(paramPhaseA);
    }

    @Override
    protected final BufferedImage applyBody(BufferedImage image) {
        final boolean sat     = paramSat.getValue();
        final double  factorR = paramFactorR.getValue();
        final double  phaseA  = Math.PI * paramPhaseA.getValue() / 180;
        final int width  = image.getWidth();
        final int height = image.getHeight();
        final BufferedImage result = new BufferedImage(width, height, image.getType());
        for (int toY = 0; toY < height; toY++) {
            for (int toX = 0; toX < width; toX++) {
                final int in = image.getRGB(toX, toY);
                final float[] in_hsb = Color.RGBtoHSB(
                    (in >> 16) & 0xFF,
                    (in >> 8)  & 0xFF,
                     in        & 0xFF,
                null);
                final float in_h = in_hsb[0];
                final float in_s = in_hsb[1];
                final float in_b = in_hsb[2];
                final double r = factorR * (sat ? in_s : in_b);
                final double a = 2 * Math.PI * in_h + phaseA;
                int fromX = toX + (int) (r * Math.sin(a));
                int fromY = toY + (int) (r * Math.cos(a));
                fromX = Math.max(0, Math.min(width  - 1, fromX));
                fromY = Math.max(0, Math.min(height - 1, fromY));
                result.setRGB(toX, toY, image.getRGB(fromX, fromY));
            }
        }
        return result;
    }

}
