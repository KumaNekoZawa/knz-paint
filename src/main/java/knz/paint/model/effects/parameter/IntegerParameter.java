package knz.paint.model.effects.parameter;

public class IntegerParameter extends AbstractLabeledParameter {

    private String unit;
    private int min, def, max;
    private int value;

    public IntegerParameter(String name, int min, int def, int max) {
        this(name, "", min, def, max);
    }

    public IntegerParameter(String name, String unit, int min, int def, int max) {
        super(name);
        this.unit = unit;
        this.min = min;
        this.def = def;
        this.max = max;
    }

    @Override
    public void reset(int width, int height) {
        this.value = def;
    }

    @Override
    public String getLabelText() {
        return getName() + ": " + String.format("%d", value) + unit;
    }

    public String getUnit() {
        return unit;
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
