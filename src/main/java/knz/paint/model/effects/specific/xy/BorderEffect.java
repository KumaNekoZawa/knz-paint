package knz.paint.model.effects.specific.xy;

import java.awt.Color;

import knz.paint.model.effects.parameter.ColorParameter;
import knz.paint.model.effects.parameter.IntegerParameter;

public class BorderEffect extends AbstractXYEffect {

    private IntegerParameter paramSize  = new IntegerParameter("Size", 1, 1, 100);
    private ColorParameter   paramColor = new ColorParameter("Color", Color.BLACK);

    public BorderEffect() {
        super("Border");
        this.parameters.add(paramSize);
        this.parameters.add(paramColor);
    }

    @Override
    protected void filter(int width, int height, int x, int y) {
        final int   size  = paramSize.getValue();
        final Color color = paramColor.getValue();
        if (!(size <= x && x < width  - size
           && size <= y && y < height - size)) {
            out_affected = true;
            out_color = color;
        }
    }

}
