package knz.paint.model.effects;

public class EffectParameter {

    private String name;
    private double min, def, max;
    private double value;

    public EffectParameter(String name, double min, double def, double max) {
        super();
        this.name = name;
        this.min = min;
        this.def = def;
        this.max = max;
        this.value = def;
    }

    public String getName() {
        return name;
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

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

}
