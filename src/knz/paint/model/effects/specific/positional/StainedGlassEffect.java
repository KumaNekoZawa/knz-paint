package knz.paint.model.effects.specific.positional;

import java.util.Arrays;

import knz.paint.model.effects.parameter.IntegerParameter;

public class StainedGlassEffect extends AbstractPositionalEffect {

    private IntegerParameter paramTiles = new IntegerParameter("Tiles", 1, 100, 1000);

    private int[] pixelsX, pixelsY;

    public StainedGlassEffect() {
        super("Stained glass");
        this.parameters.add(paramTiles);
    }

    @Override
    protected void applyHead(int width, int height) {
        final int tiles = paramTiles.getValue();
        pixelsX = new int[tiles];
        pixelsY = new int[tiles];
        for (int i = 0; i < tiles; i++) {
            pixelsX[i] = (int) (Math.random() * width);
            pixelsY[i] = (int) (Math.random() * height);
        }
        Arrays.sort(pixelsX);
        /* only sort x, do not sort y! */
    }

    @Override
    protected void filter(int width, int height, int toX, int toY) {
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
