package knz.paint.model.effects.specific.positional;

import knz.paint.model.effects.parameter.BooleanParameter;

public class FlipEffect extends AbstractPositionalEffect {

    private BooleanParameter paramFlipX = new BooleanParameter("Flip horizontally", false);
    private BooleanParameter paramFlipY = new BooleanParameter("Flip vertically",   false);

    public FlipEffect() {
        super("Flip");
        this.parameters.add(paramFlipX);
        this.parameters.add(paramFlipY);
    }

    @Override
    protected void filter(int width, int height, int toX, int toY) {
        final boolean flipX = paramFlipX.getValue();
        final boolean flipY = paramFlipY.getValue();
        fromX = flipX ? width  - toX - 1 : toX;
        fromY = flipY ? height - toY - 1 : toY;
    }

}
