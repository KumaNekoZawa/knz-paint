package knz.paint.model.effects.specific.rgba;

import knz.paint.model.effects.parameter.DoubleParameter;
import knz.paint.model.effects.parameter.Preset;
import knz.paint.model.effects.parameter.PresetParameter;

public class GrayscaleEffect extends AbstractRGBAEffect {

    private PresetParameter paramPresets = new PresetParameter(
        new String[] { "Red", "Green", "Blue" },
        new Preset("Average RGB",             0.3333, 0.3334, 0.3333),
        new Preset("Y'UV/Y'IQ PAL/NTSC",      0.299,  0.587,  0.114 ),
        new Preset("ITU-R BT.709 / CIE 1931", 0.2126, 0.7152, 0.0722),
        new Preset("ITU-R BT.2100",           0.2627, 0.6780, 0.0593)
    );
    private DoubleParameter paramR = new DoubleParameter("Red",   0, 0.3333, 1, 10000);
    private DoubleParameter paramG = new DoubleParameter("Green", 0, 0.3334, 1, 10000);
    private DoubleParameter paramB = new DoubleParameter("Blue",  0, 0.3333, 1, 10000);

    public GrayscaleEffect() {
        super("Grayscale", false, false);
        this.parameters.add(paramPresets);
        this.parameters.add(paramR);
        this.parameters.add(paramG);
        this.parameters.add(paramB);
    }

    @Override
    protected void filter(int in_r, int in_g, int in_b, int in_a) {
        final double r = paramR.getValue();
        final double g = paramG.getValue();
        final double b = paramB.getValue();
        final int gray = (int) (r * in_r + g * in_g + b * in_b);
        out_r = gray;
        out_g = gray;
        out_b = gray;
        out_a = in_a;
    }

}
