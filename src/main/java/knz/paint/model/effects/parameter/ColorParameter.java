package knz.paint.model.effects.parameter;

import java.awt.Color;

public class ColorParameter extends AbstractLabeledParameter {

    private Color def;
    private Color value;

    public ColorParameter(String name, Color def) {
        super(name);
        this.def = def;
    }

    @Override
    public void reset(int width, int height) {
        this.value = def;
    }

    @Override
    public String getLabelText() {
        return getName() + ": 0x" + String.format("%08X", value.getRGB());
    }

    public Color getDef() {
        return def;
    }

    public Color getValue() {
        return value;
    }

    public void setValue(Color value) {
        this.value = value;
    }

}
