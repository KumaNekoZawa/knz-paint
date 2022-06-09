package knz.paint.model.effects.specific.graphics;

import java.awt.Color;
import java.awt.Graphics2D;

import knz.paint.model.effects.parameter.DoubleParameter;
import knz.paint.model.effects.parameter.IntegerParameter;

public class TadaEffect extends AbstractGraphicsEffect {

    private IntegerParameter paramBrightness      = new IntegerParameter("Brightness",   0x00, 0x00, 0xFF);
    private IntegerParameter paramTransparency    = new IntegerParameter("Transparency", 0x00, 0xFF, 0xFF);
    private IntegerParameter paramAngleShift      = new IntegerParameter("Angle shift",  "°", 1, 1, 10);
    private IntegerParameter paramAngleFactor     = new IntegerParameter("Angle factor", "°", 1, 1, 10);
    private DoubleParameter  paramInnerDistance   = new DoubleParameter("Inner distance", 0, 0.33, 1);
    private DoubleParameter  paramThicknessShift  = new DoubleParameter("Thickness shift",  "°", 0, 0, 10);
    private DoubleParameter  paramThicknessFactor = new DoubleParameter("Thickness factor", "°", 0, 1, 10);

    public TadaEffect() {
        super("Ta-dah effect");
        this.parameters.add(paramBrightness);
        this.parameters.add(paramTransparency);
        this.parameters.add(paramAngleShift);
        this.parameters.add(paramAngleFactor);
        this.parameters.add(paramInnerDistance);
        this.parameters.add(paramThicknessShift);
        this.parameters.add(paramThicknessFactor);
    }

    @Override
    protected void filter(int width, int height, Graphics2D graphics2D) {
        final int    brightness      = paramBrightness.getValue();
        final int    transparency    = paramTransparency.getValue();
        final int    angleShift      = paramAngleShift.getValue();
        final int    angleFactor     = paramAngleFactor.getValue();
        final double innerDistance   = paramInnerDistance.getValue();
        final double thicknessShift  = paramThicknessShift.getValue();
        final double thicknessFactor = paramThicknessFactor.getValue();
        final int cx = width  / 2;
        final int cy = height / 2;
        final double ro = 0.75 * Math.max(width, height);
        graphics2D.setColor(new Color(brightness, brightness, brightness, transparency));
        for (int a = 0; a < 360; a += (int) (angleFactor * Math.random()) + angleShift) {
            final double ri = ((1 - innerDistance) * Math.random() + innerDistance) * ro;
            final double thickness = thicknessFactor * Math.random() + thicknessShift;

            /* inner */
            final double a1 = a * Math.PI / 180;
            final int x1 = (int) (ri * Math.sin(a1)) + cx;
            final int y1 = (int) (ri * Math.cos(a1)) + cy;
            /* outer1 */
            final double a2 = (a - thickness) * Math.PI / 180;
            final int x2 = (int) (ro * Math.sin(a2)) + cx;
            final int y2 = (int) (ro * Math.cos(a2)) + cy;
            /* outer2 */
            final double a3 = (a + thickness) * Math.PI / 180;
            final int x3 = (int) (ro * Math.sin(a3)) + cx;
            final int y3 = (int) (ro * Math.cos(a3)) + cy;

            graphics2D.fillPolygon(
                new int[] { x1, x2, x3 },
                new int[] { y1, y2, y3 },
            3);
        }
    }

}
