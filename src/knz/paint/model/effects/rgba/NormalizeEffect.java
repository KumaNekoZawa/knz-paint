package knz.paint.model.effects.rgba;

import knz.paint.model.effects.DoubleParameter;

public class NormalizeEffect extends RGBAEffect {

    private DoubleParameter paramMin = new DoubleParameter("Min", 0, 0, 1);
    private DoubleParameter paramMax = new DoubleParameter("Max", 0, 1, 1);

    public NormalizeEffect() {
        super("Normalize", true, true);
        this.parameters.add(paramMin);
        this.parameters.add(paramMax);
    }

    @Override
    public void filter(int x, int y, int in_r, int in_g, int in_b, int in_a) {
        final double min = paramMin.getValue();
        final double max = paramMax.getValue();
        out_r = (int) (0xFF * (in_r / (double) 0xFF - min) / (max - min));
        out_g = (int) (0xFF * (in_g / (double) 0xFF - min) / (max - min));
        out_b = (int) (0xFF * (in_b / (double) 0xFF - min) / (max - min));
        out_a = (int) (0xFF * (in_a / (double) 0xFF - min) / (max - min));
    }

}
