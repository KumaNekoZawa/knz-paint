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
    private DoubleParameter paramRed   = new DoubleParameter("Red",   0, 0.3333, 1, 10000);
    private DoubleParameter paramGreen = new DoubleParameter("Green", 0, 0.3334, 1, 10000);
    private DoubleParameter paramBlue  = new DoubleParameter("Blue",  0, 0.3333, 1, 10000);

    public GrayscaleEffect() {
        super("Grayscale", false, false);
        this.parameters.add(paramPresets);
        this.parameters.add(paramRed);
        this.parameters.add(paramGreen);
        this.parameters.add(paramBlue);
    }

    @Override
    protected void filter(int x, int y, int in_r, int in_g, int in_b, int in_a) {
        final double red   = paramRed.getValue();
        final double green = paramGreen.getValue();
        final double blue  = paramBlue.getValue();
        final int gray = (int) (red * in_r + green * in_g + blue * in_b);
        out_r = gray;
        out_g = gray;
        out_b = gray;
    }

}
