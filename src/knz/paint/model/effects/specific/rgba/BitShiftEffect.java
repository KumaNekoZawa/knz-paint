package knz.paint.model.effects.specific.rgba;

import knz.paint.model.effects.BooleanParameter;
import knz.paint.model.effects.IntegerParameter;

public class BitShiftEffect extends AbstractRGBAEffect {

    private BooleanParameter paramRotate = new BooleanParameter("Rotate", false);
    private IntegerParameter paramAmount = new IntegerParameter("Amount", -8, 0, 8);

    public BitShiftEffect() {
        super("Bit shift/rotate", true, true);
        this.parameters.add(paramRotate);
        this.parameters.add(paramAmount);
    }

    @Override
    protected void filter(int x, int y, int in_r, int in_g, int in_b, int in_a) {
        final boolean rotate = paramRotate.getValue();
        int amount = paramAmount.getValue();
        if (amount > 0) {
            out_r = shiftOfRotateRight(rotate, in_r, amount);
            out_g = shiftOfRotateRight(rotate, in_g, amount);
            out_b = shiftOfRotateRight(rotate, in_b, amount);
            out_a = shiftOfRotateRight(rotate, in_a, amount);
        } else if (amount < 0) {
            amount = -amount;
            out_r = shiftOfRotateLeft(rotate, in_r, amount);
            out_g = shiftOfRotateLeft(rotate, in_g, amount);
            out_b = shiftOfRotateLeft(rotate, in_b, amount);
            out_a = shiftOfRotateLeft(rotate, in_a, amount);
        }
    }

    private static int shiftOfRotateRight(boolean rotate, int i, int amount) {
        if (rotate) {
            return (((i << (8 - amount)) & 0xFF) | (i >>> amount)) & 0xFF;
        } else {
            return (i >>> amount) & 0xFF;
        }
    }

    private static int shiftOfRotateLeft(boolean rotate, int i, int amount) {
        if (rotate) {
            return (((i >>> (8 - amount)) & 0xFF) | (i << amount)) & 0xFF;
        } else {
            return (i << amount) & 0xFF;
        }
    }

}
