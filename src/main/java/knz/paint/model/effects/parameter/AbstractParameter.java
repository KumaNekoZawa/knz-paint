package knz.paint.model.effects.parameter;

public abstract class AbstractParameter {

    private String name;

    public AbstractParameter(String name) {
        super();
        this.name = name;
    }

    public abstract void reset(int width, int height);

    public final String getName() {
        return name;
    }

}
