package knz.paint.model.effects;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public abstract class Effect {

    private String name;
    protected List<AbstractParameter> parameters = new ArrayList<>();

    public Effect(String name) {
        super();
        this.name = name;
    }

    public abstract BufferedImage apply(BufferedImage image);

    public String getName() {
        return name;
    }

    public List<AbstractParameter> getParameters() {
        return parameters;
    }

}
