package knz.paint.model.effects;

public class DoubleParameter extends AbstractParameter {

    private double min, def, max, resolution;
    private double value;
    private boolean set;

    public DoubleParameter(String name, double min, double def, double max) {
        this(name, min, def, max, 100);
    }

    // this could be made public, if there is an effect, that needs to have a resolution != 100
    private DoubleParameter(String name, double min, double def, double max, double resolution) {
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
        return getName() + ": " + String.format("%.2f", value);
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
