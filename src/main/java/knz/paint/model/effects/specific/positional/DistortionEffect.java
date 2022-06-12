package knz.paint.model.effects.specific.positional;

import java.awt.Point;

import knz.paint.model.effects.parameter.BorderFillStrategy;
import knz.paint.model.effects.parameter.PointParameter;

public class DistortionEffect extends AbstractPositionalEffect {

    private PointParameter paramTL = new PointParameter("Top left",     0, 0);
    private PointParameter paramTR = new PointParameter("Top right",    1, 0);
    private PointParameter paramBR = new PointParameter("Bottom right", 1, 1);
    private PointParameter paramBL = new PointParameter("Bottom left",  0, 1);

    public DistortionEffect() {
        super("Distortion", BorderFillStrategy.EXTEND_EDGES);
        this.parameters.add(paramTL);
        this.parameters.add(paramTR);
        this.parameters.add(paramBR);
        this.parameters.add(paramBL);
    }

    @Override
    protected void filter(int width, int height, int toX, int toY) {
        final Point tl = paramTL.getValue();
        final Point tr = paramTR.getValue();
        final Point br = paramBR.getValue();
        final Point bl = paramBL.getValue();
        final int w = width  - 1;
        final int h = height - 1;
        // FIXME this effect doesn't seem to work as expected, the edges curve for some reason
        fromX = w * (toX - tl.x) * (h - toY) / Math.max(1, tr.x - tl.x) / h
              + w * (toX - bl.x) *      toY  / Math.max(1, br.x - bl.x) / h;
        fromY = h * (toY - tl.y) * (w - toX) / Math.max(1, bl.y - tl.y) / w
              + h * (toY - tr.y) *      toX  / Math.max(1, br.y - tr.y) / w;
    }

}
