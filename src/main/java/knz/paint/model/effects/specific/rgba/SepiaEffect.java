package knz.paint.model.effects.specific.rgba;

import knz.paint.model.effects.parameter.DoubleParameter;
import knz.paint.model.effects.parameter.Preset;
import knz.paint.model.effects.parameter.PresetParameter;

public class SepiaEffect extends AbstractRGBAEffect {

    private PresetParameter paramPresets = new PresetParameter(
        new String[] {
            "Red: red",   "Red: green",   "Red: blue",
            "Green: red", "Green: green", "Green: blue",
            "Blue: red",  "Blue: green",  "Blue: blue",
        },
        new Preset("Sepia",
            0.393, 0.769, 0.189,
            0.349, 0.686, 0.168,
            0.272, 0.534, 0.131
        ),
        new Preset("Reduce contrast",
            0.50, 0.25, 0.25,
            0.25, 0.50, 0.25,
            0.25, 0.25, 0.50
        )
    );
    private DoubleParameter paramRR = new DoubleParameter("Red: red",     0, 0.393, 1, 1000);
    private DoubleParameter paramRG = new DoubleParameter("Red: green",   0, 0.769, 1, 1000);
    private DoubleParameter paramRB = new DoubleParameter("Red: blue",    0, 0.189, 1, 1000);
    private DoubleParameter paramGR = new DoubleParameter("Green: red",   0, 0.349, 1, 1000);
    private DoubleParameter paramGG = new DoubleParameter("Green: green", 0, 0.686, 1, 1000);
    private DoubleParameter paramGB = new DoubleParameter("Green: blue",  0, 0.168, 1, 1000);
    private DoubleParameter paramBR = new DoubleParameter("Blue: red",    0, 0.272, 1, 1000);
    private DoubleParameter paramBG = new DoubleParameter("Blue: green",  0, 0.534, 1, 1000);
    private DoubleParameter paramBB = new DoubleParameter("Blue: blue",   0, 0.131, 1, 1000);

    public SepiaEffect() {
        super("Sepia", false, false);
        this.parameters.add(paramPresets);
        this.parameters.add(paramRR);
        this.parameters.add(paramRG);
        this.parameters.add(paramRB);
        this.parameters.add(paramGR);
        this.parameters.add(paramGG);
        this.parameters.add(paramGB);
        this.parameters.add(paramBR);
        this.parameters.add(paramBG);
        this.parameters.add(paramBB);
    }

    @Override
    protected void filter(int in_r, int in_g, int in_b, int in_a) {
        final double rr = paramRR.getValue();
        final double rg = paramRG.getValue();
        final double rb = paramRB.getValue();
        final double gr = paramGR.getValue();
        final double gg = paramGG.getValue();
        final double gb = paramGB.getValue();
        final double br = paramBR.getValue();
        final double bg = paramBG.getValue();
        final double bb = paramBB.getValue();
        out_r = (int) (rr * in_r + rg * in_g + rb * in_b);
        out_g = (int) (gr * in_r + gg * in_g + gb * in_b);
        out_b = (int) (br * in_r + bg * in_g + bb * in_b);
    }

}
