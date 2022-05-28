package knz.paint.model.effects;

public class IntegerParameter extends AbstractParameter {

    private int min, def, max;
    private int value;

    public IntegerParameter(String name, int min, int def, int max) {
        super(name);
        this.min = min;
        this.def = def;
        this.max = max;
        this.value = def;
    }

    @Override
    public void reset() {
        this.value = def;
    }

    @Override
    public String getLabelText() {
        return getName() + ": " + String.format("%d", value);
    }

    public int getMin() {
        return min;
    }

    public int getDef() {
        return def;
    }

    public int getMax() {
        return max;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
