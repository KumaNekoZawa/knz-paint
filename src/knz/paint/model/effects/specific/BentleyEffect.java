package knz.paint.model.effects.specific;

import java.awt.Color;
import java.awt.image.BufferedImage;

import knz.paint.model.effects.BooleanParameter;
import knz.paint.model.effects.DoubleParameter;

public class BentleyEffect extends AbstractEffect {

    private BooleanParameter paramSat    = new BooleanParameter("Saturation instead of Brightness", false);
    private DoubleParameter  paramFactor = new DoubleParameter("Factor", -250, 0, 250);

    public BentleyEffect() {
        super("Bentley effect");
        this.parameters.add(paramSat);
        this.parameters.add(paramFactor);
    }

    @Override
    public BufferedImage applyHelper(BufferedImage image) {
        final boolean sat    = paramSat.getValue();
        final double  factor = paramFactor.getValue();
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
                final float in_s = in_hsb[1];
                final float in_b = in_hsb[2];
                int fromX = toX;
                int fromY = toY + (int) (factor * (sat ? in_s : in_b));
                fromX = Math.max(0, Math.min(width  - 1, fromX));
                fromY = Math.max(0, Math.min(height - 1, fromY));
                result.setRGB(toX, toY, image.getRGB(fromX, fromY));
            }
        }
        return result;
    }

}
