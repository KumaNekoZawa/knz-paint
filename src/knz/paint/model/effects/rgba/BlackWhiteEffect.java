package knz.paint.model.effects.rgba;

import knz.paint.model.effects.BooleanParameter;
import knz.paint.model.effects.DoubleParameter;

public class BlackWhiteEffect extends RGBAEffect {

    private BooleanParameter random = new BooleanParameter("Random", true);
    private DoubleParameter threshold = new DoubleParameter("Threshold", 0, 0.5, 1);

    public BlackWhiteEffect() {
        super("Black & White");
        this.parameters.add(random);
        this.parameters.add(threshold);
    }

    @Override
    public void filter() {
        final int gray = (r + g + b) / 3;
        final double t = random.getValue()
            ? Math.random()
            : threshold.getValue();
        final int bw = gray < (int) (t * 0x100) ? 0 : 255;
        r = bw;
        g = bw;
        b = bw;
    }

}
