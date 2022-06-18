package knz.paint.model.effects.specific.area;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import knz.paint.model.effects.parameter.IntegerParameter;
import knz.paint.model.effects.specific.AbstractEffect;

public class OilPaintingEffect extends AbstractEffect {

    private IntegerParameter paramAreaX = new IntegerParameter("Area width",  0, 0, 10);
    private IntegerParameter paramAreaY = new IntegerParameter("Area height", 0, 0, 10);

    public OilPaintingEffect() {
        super("Area.Oil painting");
        this.parameters.add(paramAreaX);
        this.parameters.add(paramAreaY);
    }

    @Override
    protected final void applyHead(BufferedImage image) {
        applyHead();
    }

    protected void applyHead() {
    }

    @Override
    protected final BufferedImage applyBody(BufferedImage image) {
        final int areaX = paramAreaX.getValue();
        final int areaY = paramAreaY.getValue();
        final int width  = image.getWidth();
        final int height = image.getHeight();
        final BufferedImage result = new BufferedImage(width, height, image.getType());
        final Map<Integer, Integer> values = new HashMap<>();
        final List<Integer> mostFrequentIn = new ArrayList<>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                values.clear();
                for (int dy = Math.max(0, y - areaY); dy <= Math.min(height - 1, y + areaY); dy++) {
                    for (int dx = Math.max(0, x - areaX); dx <= Math.min(width - 1, x + areaX); dx++) {
                        final int in = image.getRGB(dx, dy);
                        if (values.containsKey(in)) {
                            values.put(in, values.get(in) + 1);
                        } else {
                            values.put(in, 1);
                        }
                    }
                }
                mostFrequentIn.clear();
                int maxCount = 0;
                for (final Integer in : values.keySet()) {
                    final Integer count = values.get(in);
                    if (count > maxCount) {
                        mostFrequentIn.clear();
                        mostFrequentIn.add(in);
                        maxCount = count;
                    } else if (count == maxCount) {
                        mostFrequentIn.add(in);
                    }
                }
                final int out = mostFrequentIn.get((int) (Math.random() * mostFrequentIn.size()));
                result.setRGB(x, y, out);
            }
        }
        return result;
    }

    @Override
    protected final void applyFoot(BufferedImage image) {
        applyFoot();
    }

    protected void applyFoot() {
    }

}
