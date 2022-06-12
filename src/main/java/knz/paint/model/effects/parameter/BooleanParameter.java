package knz.paint.model.effects.parameter;

public class BooleanParameter extends AbstractParameter {

    private boolean def;
    private boolean value;

    public BooleanParameter(String name, boolean def) {
        super(name);
        this.def = def;
    }

    @Override
    public void reset(int width, int height) {
        this.value = def;
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
