package knz.paint.model.effects.specific;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import knz.paint.model.effects.AbstractParameter;

public abstract class AbstractEffect {

    private String name;
    protected List<AbstractParameter> parameters = new ArrayList<>();

    public AbstractEffect(String name) {
        super();
        this.name = name;
    }

    public final BufferedImage apply(BufferedImage image) {
        init(image.getWidth(), image.getHeight());
        return applyHelper(image);
    }

    protected abstract BufferedImage applyHelper(BufferedImage image);

    public void init(int width, int height) {
    }

    public String getName() {
        return name;
    }

    public List<AbstractParameter> getParameters() {
        return parameters;
    }

}
