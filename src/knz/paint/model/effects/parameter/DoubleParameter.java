package knz.paint.model.effects.parameter;

public class DoubleParameter extends AbstractParameter {

    private double min, def, max, resolution;
    private double value;
    private boolean set;

    public DoubleParameter(String name, double min, double def, double max) {
        this(name, min, def, max, 100);
    }

    public DoubleParameter(String name, double min, double def, double max, double resolution) {
        super(name);
        this.min = min;
        this.def = def;
        this.max = max;
        this.resolution = resolution;
        reset();
    }

    @Override
    public void reset() {
        this.value = def;
        this.set = false;
    }

    @Override
    public String getLabelText() {
        return getName() + ": " + String.format("%." + (int) Math.log10(resolution) + "f", value);
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
