package knz.paint.model.effects.parameter;

public abstract class AbstractLabeledParameter extends AbstractParameter {

    public AbstractLabeledParameter(String name) {
        super(name);
    }

    public abstract String getLabelText();

}
