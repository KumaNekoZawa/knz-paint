package knz.paint.model.effects.specific.rgba;

import knz.paint.model.effects.parameter.BooleanParameter;
import knz.paint.model.effects.parameter.IntegerParameter;

public class BitShiftEffect extends AbstractRGBAEffect {

    private BooleanParameter paramRotate = new BooleanParameter("Rotate", false);
    private IntegerParameter paramBits   = new IntegerParameter("Bits", " bit", -8, 0, 8);

    public BitShiftEffect() {
        super("Bit shift/rotate", true, true);
        this.parameters.add(paramRotate);
        this.parameters.add(paramBits);
    }

    @Override
    protected void filter(int in_r, int in_g, int in_b, int in_a) {
        final boolean rotate = paramRotate.getValue();
        int bits = paramBits.getValue();
        if (bits > 0) {
            out_r = shiftOfRotateRight(rotate, in_r, bits);
            out_g = shiftOfRotateRight(rotate, in_g, bits);
            out_b = shiftOfRotateRight(rotate, in_b, bits);
            out_a = shiftOfRotateRight(rotate, in_a, bits);
        } else if (bits < 0) {
            bits = -bits;
            out_r = shiftOfRotateLeft(rotate, in_r, bits);
            out_g = shiftOfRotateLeft(rotate, in_g, bits);
            out_b = shiftOfRotateLeft(rotate, in_b, bits);
            out_a = shiftOfRotateLeft(rotate, in_a, bits);
        }
    }

    private static int shiftOfRotateRight(boolean rotate, int i, int bits) {
        if (rotate) {
            return (((i << (8 - bits)) & 0xFF) | (i >>> bits)) & 0xFF;
        } else {
            return (i >>> bits) & 0xFF;
        }
    }

    private static int shiftOfRotateLeft(boolean rotate, int i, int bits) {
        if (rotate) {
            return (((i >>> (8 - bits)) & 0xFF) | (i << bits)) & 0xFF;
        } else {
            return (i << bits) & 0xFF;
        }
    }

}
