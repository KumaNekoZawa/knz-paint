package knz.paint.model.effects.positional;

import knz.paint.model.effects.IntegerParameter;

/* also called Pixelize */
public class MosaicEffect extends PositionalEffect {

    private IntegerParameter size = new IntegerParameter("Size", 1, 1, 100);

    public MosaicEffect() {
        super("Mosaic");
        this.parameters.add(size);
    }

    @Override
    public void filter(int width, int height, int toX, int toY) {
        final int s = size.getValue();
        fromX = (toX / s) * s;
        fromY = (toY / s) * s;
    }

}
