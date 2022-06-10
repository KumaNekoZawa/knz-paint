package knz.paint.model.effects.specific;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import knz.paint.model.effects.parameter.AbstractParameter;

public abstract class AbstractEffect {

    public static final int MAX_FACTOR   = 2;
    public static final int MAX_EXPONENT = 2;
    public static final int MIN_ANGLE = -180;
    public static final int MAX_ANGLE =  180;

    private String name;
    protected List<AbstractParameter> parameters = new ArrayList<>();

    public AbstractEffect(String name) {
        super();
        this.name = name;
    }

    public final BufferedImage apply(BufferedImage image) {
        applyHead(image);
        final BufferedImage result = applyBody(image);
        applyFoot(image);
        return result;
    }

    protected void applyHead(BufferedImage image) {
    }

    protected abstract BufferedImage applyBody(BufferedImage image);

    protected void applyFoot(BufferedImage image) {
    }

    public final String getParentName() {
        return name.contains(".")
            ? name.substring(0, name.lastIndexOf("."))
            : "";
    }

    public final String getFinalName() {
        return name.contains(".")
            ? name.substring(name.lastIndexOf(".") + 1)
            : name;
    }

    public final String getName() {
        return name;
    }

    public final List<AbstractParameter> getParameters() {
        return parameters;
    }

}
