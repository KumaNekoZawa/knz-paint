package knz.paint.model.effects.specific.positional;

import java.awt.image.BufferedImage;

import knz.paint.model.effects.parameter.BorderFillStrategy;
import knz.paint.model.effects.parameter.BorderFillStrategyParameter;
import knz.paint.model.effects.specific.AbstractEffect;

public abstract class AbstractPositionalEffect extends AbstractEffect {

    private BorderFillStrategyParameter paramBorderFillStrategy;

    protected int fromX, fromY;

    public AbstractPositionalEffect(String name) {
        super("Positional." + name);
    }

    public AbstractPositionalEffect(String name, BorderFillStrategy defaultBorderFillStrategy) {
        super("Positional." + name);
        this.paramBorderFillStrategy = new BorderFillStrategyParameter(defaultBorderFillStrategy);
        this.parameters.add(paramBorderFillStrategy);
    }

    @Override
    protected final void applyHead(BufferedImage image) {
        applyHead(image.getWidth(), image.getHeight());
    }

    protected void applyHead(int width, int height) {
    }

    @Override
    protected final BufferedImage applyBody(BufferedImage image) {
        final BorderFillStrategy borderFillStrategy = paramBorderFillStrategy != null
            ? paramBorderFillStrategy.getValue()
            : BorderFillStrategy.EXTEND_EDGES;
        final int width  = image.getWidth();
        final int height = image.getHeight();
        final BufferedImage result = new BufferedImage(width, height, image.getType());
        for (int toY = 0; toY < height; toY++) {
            for (int toX = 0; toX < width; toX++) {
                fromX = toX;
                fromY = toY;
                filter(width, height, toX, toY);
                final int out;
                switch (borderFillStrategy) {
                case FILL_TRANSPARENT:
                case FILL_BLACK:
                case FILL_GRAY:
                case FILL_WHITE:
                    if (0 <= fromX && fromX < width
                     && 0 <= fromY && fromY < height) {
                        out = image.getRGB(fromX, fromY);
                    } else {
                        switch (borderFillStrategy) {
                        case FILL_TRANSPARENT:
                            out = 0x00000000;
                            break;
                        case FILL_BLACK:
                            out = 0xFF000000;
                            break;
                        case FILL_GRAY:
                            out = 0xFF808080;
                            break;
                        case FILL_WHITE:
                            out = 0xFFFFFFFF;
                            break;
                        default:
                            throw new AssertionError();
                        }
                    }
                    break;
                case EXTEND_EDGES:
                    fromX = Math.max(0, Math.min(width  - 1, fromX));
                    fromY = Math.max(0, Math.min(height - 1, fromY));
                    out = image.getRGB(fromX, fromY);
                    break;
                case ROLLOVER:
                    fromX = Math.floorMod(fromX, width);
                    fromY = Math.floorMod(fromY, height);
                    out = image.getRGB(fromX, fromY);
                    break;
                default:
                    throw new AssertionError();
                }
                result.setRGB(toX, toY, out);
            }
        }
        return result;
    }

    @Override
    protected final void applyFoot(BufferedImage image) {
        applyFoot(image.getWidth(), image.getHeight());
    }

    protected void applyFoot(int width, int height) {
    }

    protected abstract void filter(int width, int height, int toX, int toY);

}
