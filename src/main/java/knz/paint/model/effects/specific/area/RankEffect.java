package knz.paint.model.effects.specific.area;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Comparator;

import knz.paint.model.effects.parameter.IntegerParameter;
import knz.paint.model.effects.parameter.Preset;
import knz.paint.model.effects.parameter.PresetParameter;
import knz.paint.model.effects.specific.AbstractEffect;

public class RankEffect extends AbstractEffect {

    private PresetParameter paramPresets = new PresetParameter(
        new String[] { "Area width", "Area height", "Rank" },
        new Preset("Default", 0, 0, 4),
        new Preset("Minimum", 1, 1, 0),
        new Preset("Medium",  1, 1, 4),
        new Preset("Maximum", 1, 1, 8)
    );
    private IntegerParameter paramAreaX = new IntegerParameter("Area width",  0, 0, 10);
    private IntegerParameter paramAreaY = new IntegerParameter("Area height", 0, 0, 10);
    private IntegerParameter paramRank  = new IntegerParameter("Rank", 0, 4, 8);

    public RankEffect() {
        super("Area.Rank effect");
        this.parameters.add(paramPresets);
        this.parameters.add(paramAreaX);
        this.parameters.add(paramAreaY);
        this.parameters.add(paramRank);
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
        final int rank  = paramRank.getValue();
        final int ranks = paramRank.getMax() + 1;
        final int width  = image.getWidth();
        final int height = image.getHeight();
        final BufferedImage result = new BufferedImage(width, height, image.getType());
        final int maxValueCount = (2 * areaX + 1) * (2 * areaY + 1);
        final Integer[] values = new Integer[maxValueCount];
        final Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer i1, Integer i2) {
                return Integer.compare(sortkey(i1), sortkey(i2));
            }
        };
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int i = 0;
                for (int dy = Math.max(0, y - areaY); dy <= Math.min(height - 1, y + areaY); dy++) {
                    for (int dx = Math.max(0, x - areaX); dx <= Math.min(width - 1, x + areaX); dx++) {
                        values[i++] = image.getRGB(dx, dy);
                    }
                }
                Arrays.sort(values, 0, i, comparator);
                result.setRGB(x, y, values[rank * i / ranks]);
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

    private static int sortkey(int in) {
        final int in_r = (in >> 16) & 0xFF;
        final int in_g = (in >>  8) & 0xFF;
        final int in_b =  in        & 0xFF;
        return (in_r + in_g + in_b) / 3;
    }

}
