package knz.paint.model.effects.parameter;

public class IntegerArrayParameter extends AbstractLabeledParameter {

    private int sizeX, sizeY;
    private int[] def;
    private int[] values;

    public IntegerArrayParameter(String name, int sizeX, int sizeY, int[] def) {
        super(name);
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.def = def;
        this.values = new int[sizeX * sizeY];
    }

    @Override
    public void reset(int width, int height) {
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                final int i = y * sizeX + x;
                values[i] = def[i];
            }
        }
    }

    @Override
    public String getLabelText() {
        return getName() + ":";
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public int[] getDef() {
        return def;
    }

    public int getDef(int x, int y) {
        return def[y * sizeX + x];
    }

    public int[] getValues() {
        return values;
    }

    public int getValue(int x, int y) {
        return values[y * sizeX + x];
    }

    public void setValues(int[] values) {
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                final int i = y * sizeX + x;
                this.values[i] = values[i];
            }
        }
    }

    public void setValue(int x, int y, int value) {
        this.values[y * sizeX + x] = value;
    }

}
