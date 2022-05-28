package knz.paint.model.effects;

public class BooleanParameter extends AbstractParameter {

    private boolean def;
    private boolean value;

    public BooleanParameter(String name, boolean def) {
        super(name);
        this.def = def;
        reset();
    }

    @Override
    public void reset() {
        this.value = def;
    }

    @Override
    public String getLabelText() {
        return getName() + ": " + Boolean.toString(value);
    }

    public boolean getDef() {
        return def;
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

}
