package knz.paint.model.effects.parameter;

public class BorderFillStrategyParameter extends AbstractParameter {

    private BorderFillStrategy def;
    private BorderFillStrategy value;

    public BorderFillStrategyParameter(BorderFillStrategy def) {
        super("Border fill strategy");
        this.def = def;
        reset();
    }

    @Override
    public void reset() {
        this.value = def;
    }

    @Override
    public String getLabelText() {
        return getName() + ": " + value.getName();
    }

    public BorderFillStrategy getDef() {
        return def;
    }

    public BorderFillStrategy getValue() {
        return value;
    }

    public void setValue(BorderFillStrategy value) {
        this.value = value;
    }

}
