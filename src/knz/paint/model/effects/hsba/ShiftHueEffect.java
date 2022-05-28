package knz.paint.model.effects.hsba;

import knz.paint.model.effects.DoubleParameter;

public class ShiftHueEffect extends HSBAEffect {

    private DoubleParameter phase = new DoubleParameter("Phase", 0, 0, 360);

    public ShiftHueEffect() {
        super("Shift hue");
        this.parameters.add(phase);
    }

    @Override
    public void filter() {
        h += (float) (phase.getValue() / 360);
    }

}
