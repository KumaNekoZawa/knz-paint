package knz.paint.model.effects.positional;

import knz.paint.model.effects.IntegerParameter;

/* also called Pixelize */
public class MosaicEffect extends AbstractPositionalEffect {

    private IntegerParameter paramSize = new IntegerParameter("Size", 1, 1, 100);

    public MosaicEffect() {
        super("Mosaic");
        this.parameters.add(paramSize);
    }

    @Override
    public void filter(int width, int height, int toX, int toY) {
        final int size = paramSize.getValue();
        fromX = (toX / size) * size;
        fromY = (toY / size) * size;
    }

}
