package knz.paint.model.effects.specific.xy;

import knz.paint.model.effects.parameter.IntegerParameter;

/* also called "Pixelization" */
public class MosaicEffect extends AbstractXYEffect {

    private IntegerParameter paramSizeX = new IntegerParameter("Size x", 1, 1, 100);
    private IntegerParameter paramSizeY = new IntegerParameter("Size y", 1, 1, 100);

    public MosaicEffect() {
        super("Mosaic");
        this.parameters.add(paramSizeX);
        this.parameters.add(paramSizeY);
    }

    @Override
    protected void filter(int width, int height, int toX, int toY) {
        final int sizeX = paramSizeX.getValue();
        final int sizeY = paramSizeY.getValue();
        fromX = (toX / sizeX) * sizeX;
        fromY = (toY / sizeY) * sizeY;
    }

}
