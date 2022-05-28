package knz.paint.model.effects;

public abstract class AbstractParameter {

    private String name;

    public AbstractParameter(String name) {
        super();
        this.name = name;
    }

    public abstract void reset();

    public abstract String getLabelText();

    public final String getName() {
        return name;
    }

}
