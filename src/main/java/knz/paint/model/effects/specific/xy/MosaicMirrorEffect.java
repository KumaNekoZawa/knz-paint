package knz.paint.model.effects.specific.xy;

import knz.paint.model.effects.parameter.IntegerParameter;

public class MosaicMirrorEffect extends AbstractXYEffect {

    private IntegerParameter paramSizeX = new IntegerParameter("Size x", 1, 1, 100);
    private IntegerParameter paramSizeY = new IntegerParameter("Size y", 1, 1, 100);

    public MosaicMirrorEffect() {
        super("Mosaic mirror");
        this.parameters.add(paramSizeX);
        this.parameters.add(paramSizeY);
    }

    @Override
    protected void filter(int width, int height, int toX, int toY) {
        final int sizeX = paramSizeX.getValue();
        final int sizeY = paramSizeY.getValue();
        fromX = (toX / sizeX) * sizeX + (sizeX - toX % sizeX - 1);
        fromY = (toY / sizeY) * sizeY + (sizeY - toY % sizeY - 1);
    }

}
