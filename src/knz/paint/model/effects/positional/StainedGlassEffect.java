package knz.paint.model.effects.positional;

import java.util.Arrays;

import knz.paint.model.effects.IntegerParameter;

public class StainedGlassEffect extends AbstractPositionalEffect {

    private IntegerParameter paramAmount = new IntegerParameter("Amount", 1, 100, 1000);

    private int[] pixelsX, pixelsY;

    public StainedGlassEffect() {
        super("Stained glass");
        this.parameters.add(paramAmount);
    }

    @Override
    public void init(int width, int height) {
        final int amount = paramAmount.getValue();
        pixelsX = new int[amount];
        pixelsY = new int[amount];
        for (int i = 0; i < amount; i++) {
            pixelsX[i] = (int) (Math.random() * width);
            pixelsY[i] = (int) (Math.random() * height);
        }
        Arrays.sort(pixelsX); // only x, do not sort y!
    }

    @Override
    public void filter(int width, int height, int toX, int toY) {
        int closestPixel = 0;
        double closestDistance = Double.MAX_VALUE;
        int maxX = width;
        for (int i = 0; i < pixelsX.length; i++) {
            if (pixelsX[i] > maxX) {
                break;
            }
            final int dx = toX - pixelsX[i];
            final int dy = toY - pixelsY[i];
            final double distance = Math.sqrt(dx * dx + dy * dy);
            if (distance < closestDistance) {
                closestPixel = i;
                closestDistance = distance;
                maxX = toX + (int) closestDistance;
            }
        }
        fromX = pixelsX[closestPixel];
        fromY = pixelsY[closestPixel];
    }

}
