package knz.paint.model.effects.specific.positional;

import knz.paint.model.effects.parameter.IntegerParameter;

/* also called "Pixelization" */
public class MosaicEffect extends AbstractPositionalEffect {

    private IntegerParameter paramSize = new IntegerParameter("Size", 1, 1, 100);

    public MosaicEffect() {
        super("Mosaic");
        this.parameters.add(paramSize);
    }

    @Override
    protected void filter(int width, int height, int toX, int toY) {
        final int size = paramSize.getValue();
        fromX = (toX / size) * size;
        fromY = (toY / size) * size;
    }

}
