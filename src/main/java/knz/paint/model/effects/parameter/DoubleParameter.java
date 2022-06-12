package knz.paint.model.effects.parameter;

public class DoubleParameter extends AbstractLabeledParameter {

    private String unit;
    private double min, def, max, resolution;
    private double value;
    private boolean set;

    public DoubleParameter(String name, double min, double def, double max) {
        this(name, "", min, def, max, 100);
    }

    public DoubleParameter(String name, String unit, double min, double def, double max) {
        this(name, unit, min, def, max, 100);
    }

    public DoubleParameter(String name, double min, double def, double max, double resolution) {
        this(name, "", min, def, max, resolution);
    }

    public DoubleParameter(String name, String unit, double min, double def, double max, double resolution) {
        super(name);
        this.unit = unit;
        this.min = min;
        this.def = def;
        this.max = max;
        this.resolution = resolution;
    }

    @Override
    public void reset(int width, int height) {
        this.value = def;
        this.set = false;
    }

    @Override
    public String getLabelText() {
        return getName() + ": " + String.format("%." + (int) Math.log10(resolution) + "f", value) + unit;
    }

    public String getUnit() {
        return unit;
    }

    public double getMin() {
        return min;
    }

    public double getDef() {
        return def;
    }

    public double getMax() {
        return max;
    }

    public double getResolution() {
        return resolution;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
        this.set = true;
    }

    public boolean isSet() {
        return set;
    }

}
