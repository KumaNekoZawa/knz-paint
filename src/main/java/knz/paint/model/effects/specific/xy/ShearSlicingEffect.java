package knz.paint.model.effects.specific.xy;

import knz.paint.model.effects.parameter.BooleanParameter;
import knz.paint.model.effects.parameter.BorderFillStrategy;
import knz.paint.model.effects.parameter.IntegerParameter;
import knz.paint.model.effects.parameter.Preset;
import knz.paint.model.effects.parameter.PresetParameter;

/* see: Beyond Photography: The Digital Darkroom p.36-37,50-51 */
public class ShearSlicingEffect extends AbstractXYEffect {

    private PresetParameter paramPresets = new PresetParameter(
        new String[] { "Border fill strategy", "Relative", "Shift r", "Factor r", "Shift d", "Factor d" },
        new Preset("Default", BorderFillStrategy.FILL_TRANSPARENT, false,   0,  1, 0,  1),
        new Preset("Shear",   BorderFillStrategy.FILL_BLACK,       true,   -1,  3, 0,  1),
        new Preset("Slicing", BorderFillStrategy.EXTEND_EDGES,     false, -32, 64, 8, 32)
    );
    private BooleanParameter paramRelative = new BooleanParameter("Relative", false);
    private IntegerParameter paramShiftR   = new IntegerParameter("Shift r",  -100, 0, 100);
    private IntegerParameter paramFactorR  = new IntegerParameter("Factor r",    1, 1, 100);
    private IntegerParameter paramShiftD   = new IntegerParameter("Shift d",     0, 0, 100);
    private IntegerParameter paramFactorD  = new IntegerParameter("Factor d",    1, 1, 100);

    private int[] shiftX, shiftY;

    public ShearSlicingEffect() {
        super("Shear/slicing effect", BorderFillStrategy.FILL_TRANSPARENT);
        this.parameters.add(paramPresets);
        this.parameters.add(paramRelative);
        this.parameters.add(paramShiftR);
        this.parameters.add(paramFactorR);
        this.parameters.add(paramShiftD);
        this.parameters.add(paramFactorD);
    }

    @Override
    protected void applyHead(int width, int height) {
        this.shiftX = new int[height];
        applyHead(shiftX, height);
        this.shiftY = new int[width];
        applyHead(shiftY, width);
    }

    private void applyHead(int[] shift, int size) {
        final boolean relative = paramRelative.getValue();
        final int shiftR  = paramShiftR.getValue();
        final int factorR = paramFactorR.getValue();
        final int shiftD  = paramShiftD.getValue();
        final int factorD = paramFactorD.getValue();
        int r = 0, d = 0;
        for (int i = 0; i < size; i++) {
            if (d == 0) {
                r = (int) (factorR * Math.random()) + shiftR + (relative ? r : 0);
                d = (int) (factorD * Math.random()) + shiftD;
            } else {
                d--;
            }
            shift[i] = r;
        }
    }

    @Override
    protected void filter(int width, int height, int toX, int toY) {
        fromX = toX + shiftX[toY];
        fromY = toY + shiftY[toX];
    }

}
