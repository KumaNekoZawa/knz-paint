package knz.paint.model.effects.parameter;

public class Preset {

    private String name;
    private Object[] values;

    public Preset(String name, Object... values) {
        super();
        this.name = name;
        this.values = values;
    }

    public String getName() {
        return name;
    }

    public Object[] getValues() {
        return values;
    }

}
