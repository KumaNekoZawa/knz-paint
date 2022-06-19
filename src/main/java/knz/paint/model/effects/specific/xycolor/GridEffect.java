package knz.paint.model.effects.specific.xycolor;

import java.awt.Color;

import knz.paint.model.effects.parameter.ColorParameter;
import knz.paint.model.effects.parameter.IntegerParameter;

public class GridEffect extends AbstractXYToColorEffect {

    private IntegerParameter paramFieldX = new IntegerParameter("Field width",  2, 2, 100);
    private IntegerParameter paramFieldY = new IntegerParameter("Field height", 2, 2, 100);
    private IntegerParameter paramLineX  = new IntegerParameter("Line width",   0, 1, 100);
    private IntegerParameter paramLineY  = new IntegerParameter("Line height",  0, 1, 100);
    private ColorParameter   paramColor  = new ColorParameter("Color", Color.BLACK);

    public GridEffect() {
        super("Grid");
        this.parameters.add(paramFieldX);
        this.parameters.add(paramFieldY);
        this.parameters.add(paramLineX);
        this.parameters.add(paramLineY);
        this.parameters.add(paramColor);
    }

    @Override
    protected void filter(int width, int height, int x, int y) {
        final int   fieldX = paramFieldX.getValue();
        final int   fieldY = paramFieldY.getValue();
        final int   lineX  = paramLineX.getValue();
        final int   lineY  = paramLineY.getValue();
        final Color color  = paramColor.getValue();
        if ((lineX > 0 && x % fieldX < lineX)
         || (lineY > 0 && y % fieldY < lineY)) {
            out_affected = true;
            out_color = color;
        }
    }

}
